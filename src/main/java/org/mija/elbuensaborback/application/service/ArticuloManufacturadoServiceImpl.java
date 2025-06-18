package org.mija.elbuensaborback.application.service;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.global.manufacturado.ArticuloManufacturadoDetalleDto;
import org.mija.elbuensaborback.application.dto.request.manufacturado.ArticuloManufacturadoCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.manufacturado.ArticuloManufacturadoUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoBasicResponse;
import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoResponse;
import org.mija.elbuensaborback.application.helpers.ArticuloEstadoService;
import org.mija.elbuensaborback.application.mapper.ArticuloManufacturadoMapper;
import org.mija.elbuensaborback.application.service.contratos.ArticuloManufacturadoService;
import org.mija.elbuensaborback.infrastructure.persistence.entity.*;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticuloManufacturadoServiceImpl implements ArticuloManufacturadoService {

    private final ArticuloManufacturadoRepositoryImpl articuloManufacturadoRepository;
    private final CategoriaRepositoryImpl categoriaRepository;
    private final ArticuloManufacturadoDetalleRepositoryImpl articuloManufacturadoDetalleRepository;
    private final ArticuloManufacturadoMapper articuloManufacturadoMapper;
    private final ArticuloInsumoRepositoryImpl articuloInsumoRepository;
    private final ArticuloEstadoService articuloEstadoService;

    @Override
    public ArticuloManufacturadoResponse crearArticulo(ArticuloManufacturadoCreatedRequest articulo) {
        CategoriaEntity categoria = categoriaRepository.findById(articulo.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        ArticuloManufacturadoEntity articuloEntity = articuloManufacturadoMapper.toEntity(articulo);
        articuloEntity.setCategoria(categoria);

        SucursalEntity sucursal = SucursalEntity.builder().id(1L).build();
        articuloEntity.setSucursal(sucursal);

        articuloEntity.calcularPrecioCosto();

        System.out.println("COSTO CALCULADO: "+ articuloEntity.getPrecioCosto());
        System.out.println("COSTO RECIBIDO: "+ articulo.precioCosto());

        articuloEntity.setPrecioCosto(articulo.precioCosto());
        articuloEntity.setPrecioVenta(articulo.precioVenta());
        articuloEntity.setTiempoEstimadoMinutos(articulo.tiempoEstimadoMinutos());

        articuloEntity = articuloManufacturadoRepository.save(articuloEntity);

        List<ArticuloManufacturadoDetalleEntity> listaDetalles =
                articuloManufacturadoDetalleRepository.findAllByIdArticuloManufacturado(articuloEntity.getId());

        articuloEntity.setArticuloManufacturadoDetalle(listaDetalles);

        return articuloManufacturadoMapper.toResponse(articuloEntity);
    }

    @Override
    @Transactional
    public ArticuloManufacturadoResponse actualizarArticulo(Long id, ArticuloManufacturadoUpdateRequest articulo) {
        // 1. Buscar el artículo existente
        ArticuloManufacturadoEntity articuloEntity = articuloManufacturadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artículo no encontrado con ID: " + id));

        // 2. Guardar estado anterior
        boolean estabaActivo = Boolean.TRUE.equals(articuloEntity.getProductoActivo());

        // 3. Buscar la categoría
        CategoriaEntity categoria = categoriaRepository.findById(articulo.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + articulo.categoriaId()));

        // 4. Obtener todos los IDs de insumos mencionados en los detalles
        List<Long> insumoIds = articulo.articuloManufacturadoDetalle().stream()
                .map(ArticuloManufacturadoDetalleDto::articuloInsumoId)
                .distinct()
                .toList();

        // 5. Buscar todos los insumos
        Map<Long, ArticuloInsumoEntity> insumos = articuloInsumoRepository.findAllById(insumoIds).stream()
                .collect(Collectors.toMap(ArticuloInsumoEntity::getId, Function.identity()));

        // 6. Validar que no falte ninguno
        if (insumos.size() != insumoIds.size()) {
            throw new RuntimeException("Uno o más insumos no existen en la base de datos.");
        }

        // 7. Mapear cambios
        articuloManufacturadoMapper.updateEntityWithDetalles(articulo, articuloEntity, insumos);

        // 8. Validaciones de negocio
        if (articuloEntity.getPrecioCosto() != articulo.precioCosto()) {
            throw new RuntimeException("Precio costo insuficiente");
        }

        articuloEntity.setPrecioCosto(articulo.precioCosto());
        articuloEntity.setPrecioVenta(articulo.precioVenta());
        articuloEntity.setTiempoEstimadoMinutos(articulo.tiempoEstimadoMinutos());
        articuloEntity.setCategoria(categoria);

        // 9. Guardar cambios
        ArticuloManufacturadoEntity actualizado = articuloManufacturadoRepository.save(articuloEntity);

        // 10. Verificar cambios de estado y actuar
        boolean ahoraActivo = Boolean.TRUE.equals(actualizado.getProductoActivo());

        if (!estabaActivo && ahoraActivo) {
            // Reactivado: verificar promociones que lo usan
            articuloEstadoService.reactivarManufacturadoRecursivamente(actualizado);
        } else if (estabaActivo && !ahoraActivo) {
            // Desactivado: desactivar promociones relacionadas
            articuloEstadoService.desactivarManufacturadoRecursivamente(actualizado);
        }

        // 11. Retornar DTO
        return articuloManufacturadoMapper.toResponse(actualizado);
    }


    @Override
    public ArticuloManufacturadoResponse obtenerArticulo(Long id) {
        return articuloManufacturadoMapper.toResponse(articuloManufacturadoRepository.findById(id).orElse(null));
    }

    @Override
    @Transactional
    public void eliminarArticulo(Long id) {
        ArticuloManufacturadoEntity manufacturado = articuloManufacturadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el Articulo Manufacturado con id: " + id));

        manufacturado.setEsVendible(false);

        articuloEstadoService.desactivarManufacturadoRecursivamente(manufacturado);
    }

    @Override
    public Set<ArticuloManufacturadoResponse> listarArticulos() {

        List<ArticuloManufacturadoEntity> articulos = articuloManufacturadoRepository.findAll();
        System.out.println("IMAGENES");
        articulos.get(0).getImagenesUrls().forEach(image -> System.out.println(image));
        return articulos.stream().map(articuloManufacturadoMapper::toResponse).collect(Collectors.toSet());
    }

    public List<ArticuloManufacturadoBasicResponse> listarBasicArticulosManufacturados() {

        return articuloManufacturadoRepository.findAllBasicForSell().stream().map(articuloManufacturadoMapper::toBasicResponse).collect(Collectors.toList());
    }

}
