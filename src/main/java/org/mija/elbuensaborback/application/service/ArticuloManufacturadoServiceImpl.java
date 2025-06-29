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

        // Calcular el precio de costo real con sus insumos
        articuloEntity.calcularPrecioCosto();

        // Calcular el precio de venta (si tiene margen, se calcula; si no, usa el del request)
        articuloEntity.calcularPrecioVenta(articulo.precioVenta());

        // También establecer el tiempo estimado desde el request
        articuloEntity.setTiempoEstimadoMinutos(articulo.tiempoEstimadoMinutos());

        // Guardar en base de datos
        articuloEntity = articuloManufacturadoRepository.save(articuloEntity);

        // Cargar y asociar los detalles
        List<ArticuloManufacturadoDetalleEntity> listaDetalles =
                articuloManufacturadoDetalleRepository.findAllByIdArticuloManufacturado(articuloEntity.getId());
        articuloEntity.setArticuloManufacturadoDetalle(listaDetalles);

        return articuloManufacturadoMapper.toResponse(articuloEntity);
    }

    @Override
    @Transactional
    public ArticuloManufacturadoResponse actualizarArticulo(Long id, ArticuloManufacturadoUpdateRequest articuloRequest) {
        // 1. Buscar el artículo existente
        ArticuloManufacturadoEntity articuloEntity = articuloManufacturadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artículo no encontrado con ID: " + id));

        // 2. Guardar estado anterior
        boolean estabaActivo = Boolean.TRUE.equals(articuloEntity.getProductoActivo());

        // 3. Buscar la categoría
        CategoriaEntity categoria = categoriaRepository.findById(articuloRequest.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + articuloRequest.categoriaId()));

        // 4. Obtener todos los IDs de insumos mencionados en los detalles
        List<Long> insumoIds = articuloRequest.articuloManufacturadoDetalle().stream()
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
        articuloManufacturadoMapper.updateEntityWithDetalles(articuloRequest, articuloEntity, insumos);

        // 8. Validaciones de negocio
        // Validar si el precio de costo es suficiente
        if (articuloEntity.getPrecioCosto() != articuloRequest.precioCosto()) {
            throw new RuntimeException("Precio costo insuficiente");
        }

        // Si se envió precioCosto, usarlo; si no, recalcular
        if (articuloRequest.precioCosto() != null) {
            articuloEntity.setPrecioCosto(articuloRequest.precioCosto());
        } else {
            articuloEntity.calcularPrecioCosto();
        }

        // Calcular el precio de venta usando la lógica de margen o precio manual
        articuloEntity.calcularPrecioVenta(articuloRequest.precioVenta());

        // Establecer tiempo estimado y categoría
        articuloEntity.setTiempoEstimadoMinutos(articuloRequest.tiempoEstimadoMinutos());
        articuloEntity.setCategoria(categoria);


        // 10. Verificar cambios de estado y actuar
        boolean ahoraActivo = Boolean.TRUE.equals(articuloEntity.getProductoActivo());

        if (!estabaActivo && ahoraActivo) {
            // Reactivado: verificar promociones que lo usan
            articuloEntity.setProductoActivo(Boolean.FALSE); //Para no afectar el metodo recursivo que lo va a activar
            articuloEstadoService.reactivarManufacturadoRecursivamente(articuloEntity);
        } else if (estabaActivo && !ahoraActivo) {
            // Desactivado: desactivar promociones relacionadas
            articuloEntity.setProductoActivo(Boolean.TRUE); //Para no afectar el metodo recursivo que lo va a activar
            articuloEstadoService.desactivarManufacturadoRecursivamente(articuloEntity);
        }

        // 11. Retornar DTO
        return articuloManufacturadoMapper.toResponse(articuloEntity);
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
