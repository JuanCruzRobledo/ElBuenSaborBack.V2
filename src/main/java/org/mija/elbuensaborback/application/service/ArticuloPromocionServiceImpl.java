package org.mija.elbuensaborback.application.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.global.promocion.ArticuloPromocionDto;
import org.mija.elbuensaborback.application.dto.global.promocion.DetalleDto;
import org.mija.elbuensaborback.application.dto.request.promocion.ArticuloPromocionCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.promocion.ArticuloPromocionUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ArticuloPromocionMenuBasicResponse;
import org.mija.elbuensaborback.application.helpers.ArticuloEstadoService;
import org.mija.elbuensaborback.application.mapper.ArticuloPromocionMapper;
import org.mija.elbuensaborback.application.service.contratos.ArticuloPromocionService;
import org.mija.elbuensaborback.infrastructure.persistence.entity.*;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.ArticuloPromocionRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.ArticuloRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.CategoriaRepositoryImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticuloPromocionServiceImpl implements ArticuloPromocionService {

    private final ArticuloPromocionRepositoryImpl articuloPromocionRepository;
    private final ArticuloPromocionMapper articuloPromocionMapper;
    private final CategoriaRepositoryImpl categoriaRepository;
    private final ArticuloRepositoryImpl articuloRepository;
    private final ArticuloEstadoService articuloEstadoService;


    @Override
    public ArticuloPromocionDto obtenerArticuloPromocion(Long id) {

        ArticuloPromocionEntity articuloPromocionEntity = articuloPromocionRepository.findById(id).orElseThrow(()->new EntityNotFoundException("No se pudo encontrar la Promocion"));

        return articuloPromocionMapper.toResponse(articuloPromocionEntity) ;
    }

    @Override
    public List<ArticuloPromocionDto> listarArticulosPromocion() {
        return articuloPromocionRepository.findAll().stream().map(articuloPromocionMapper::toResponse).toList();
    }

    @Override
    public List<ArticuloPromocionMenuBasicResponse> listarArticulosPromocionMenu() {
        return articuloPromocionRepository.findAllPromocionesActivas().stream().map(articuloPromocionMapper::toBasicResponse).toList();
    }

    @Override
    public ArticuloPromocionDto crearPromocion(ArticuloPromocionCreatedRequest articuloPromocionCreatedRequest) {
        ArticuloPromocionEntity articuloPromocion = articuloPromocionMapper.toEntity(articuloPromocionCreatedRequest);

        //1. Calcular el precio costo de la promocion y el precio de total sin descuento
        SucursalEntity sucursal = SucursalEntity.builder().id(1L).build();
        articuloPromocion.setSucursal(sucursal);
        articuloPromocion.calcularPrecioCosto();
        articuloPromocion.calcularPrecioTotal();

        articuloPromocion = articuloPromocionRepository.save(articuloPromocion);

        return articuloPromocionMapper.toResponse(articuloPromocion);
    }

    @Override
    @Transactional
    public ArticuloPromocionDto actualizarPromocion(Long id , ArticuloPromocionUpdateRequest updateDto) {
        // 1. Buscar promoción
        ArticuloPromocionEntity promocionEntity = articuloPromocionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se pudo encontrar la Promoción"));

        // 2. Guardar estado anterior
        boolean estabaActiva = Boolean.TRUE.equals(promocionEntity.getProductoActivo());

        // 3. Buscar la categoría
        CategoriaEntity categoria = categoriaRepository.findById(updateDto.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + updateDto.categoriaId()));

        // 4. Obtener todos los IDs de artículos mencionados en los detalles
        List<Long> articuloIds = updateDto.promocionDetalle().stream()
                .map(DetalleDto::articuloId)
                .distinct()
                .toList();

        // 5. Buscar artículos base y armar mapa
        Map<Long, ArticuloEntity> articulos = articuloRepository.findAllById(articuloIds).stream()
                .collect(Collectors.toMap(ArticuloEntity::getId, Function.identity()));

        // 6. Validar que no falte ninguno
        if (articulos.size() != articuloIds.size()) {
            throw new RuntimeException("Uno o más artículos del detalle no existen en la base de datos.");
        }

        // 7. Mapear datos desde el DTO hacia la entidad (incluyendo detalles)
        articuloPromocionMapper.updateEntityWithDetalles(updateDto, promocionEntity, articulos);

        // 8. Validar precios
        if (promocionEntity.getPrecioCosto().compareTo(updateDto.precioCosto()) != 0) {
            throw new RuntimeException("Precio de costo no coincide con el esperado.");
        }

        // 9. Setear nueva categoría y costo
        promocionEntity.setCategoria(categoria);
        promocionEntity.calcularPrecioCosto();
        promocionEntity.calcularPrecioTotal();

        // 11. Verificar cambio de estado y aplicar lógica relacionada
        boolean estaActivaAhora = Boolean.TRUE.equals(promocionEntity.getProductoActivo());

        if (!estabaActiva && estaActivaAhora) {
            // Se reactivó
            promocionEntity.setProductoActivo(Boolean.FALSE); //Para no afectar el metodo recursivo que lo va a activar
            articuloEstadoService.reactivarPromocionRecursivamente(promocionEntity);
        } else if (estabaActiva && !estaActivaAhora) {
            // Se desactivó
            promocionEntity.setProductoActivo(Boolean.TRUE); //Para no afectar el metodo recursivo que lo va a activar
            articuloEstadoService.desactivarPromocionRecursivamente(promocionEntity);
        }

        // 12. Devolver DTO
        return articuloPromocionMapper.toResponse(promocionEntity);
    }

    @Override
    @Transactional
    public ArticuloPromocionDto eliminarPromocion(Long id) {
        ArticuloPromocionEntity promocion = articuloPromocionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se pudo eliminar la Promoción"));

        promocion.setEsVendible(false);

        articuloEstadoService.desactivarPromocionRecursivamente(promocion);

        return articuloPromocionMapper.toResponse(promocion);
    }



}
