package org.mija.elbuensaborback.application.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mija.elbuensaborback.application.dto.global.manufacturado.ArticuloManufacturadoDetalleDto;
import org.mija.elbuensaborback.application.dto.request.manufacturado.ArticuloManufacturadoCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.manufacturado.ArticuloManufacturadoUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoBasicResponse;
import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoResponse;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloInsumoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloManufacturadoDetalleEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloManufacturadoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ImagenArticuloEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ArticuloManufacturadoDetalleMapper.class, ImagenArticuloMapper.class })
public abstract class ArticuloManufacturadoMapper {


    // ======================= CREATE =======================
    @Mapping(target = "imagenesUrls", ignore = true)
    @Mapping(target = "categoria.id", source = "categoriaId")
    public abstract ArticuloManufacturadoEntity toEntity(ArticuloManufacturadoCreatedRequest request);


    @AfterMapping
    protected void mapImagenes(@MappingTarget ArticuloManufacturadoEntity entity, ArticuloManufacturadoCreatedRequest dto) {
        if (dto.imagenesUrls() != null) {
            Set<ImagenArticuloEntity> imagenes = dto.imagenesUrls().stream()
                    .map(url -> {
                        return ImagenArticuloEntity.builder()
                                .url(url)
                                .articulo(entity)
                                .build();
                    }).collect(Collectors.toSet());
            entity.setImagenesUrls(imagenes);
        }
    }

    @AfterMapping
    protected void relacionConManufacturado(@MappingTarget ArticuloManufacturadoEntity entity, ArticuloManufacturadoCreatedRequest dto) {
        entity.getArticuloManufacturadoDetalle().stream().forEach(detalle -> { detalle.setArticuloManufacturado(entity); });
    }


    // ======================= UPDATE =======================


    @AfterMapping
    protected void relacionConArticulo( ArticuloManufacturadoUpdateRequest updateDto, @MappingTarget ArticuloManufacturadoEntity entity){
        entity.getArticuloManufacturadoDetalle().forEach(detalle -> detalle.setArticuloManufacturado(entity));
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "articuloManufacturadoDetalle", ignore = true)
    public abstract void updateEntityFromDto(ArticuloManufacturadoUpdateRequest dto, @MappingTarget ArticuloManufacturadoEntity entity);

    public void updateEntityWithDetalles(
            ArticuloManufacturadoUpdateRequest updateDto,
            @MappingTarget ArticuloManufacturadoEntity entity,
            Map<Long, ArticuloInsumoEntity> insumos
    ) {
        // 1. Mapeo de campos básicos (excepto detalles)
        updateEntityFromDto(updateDto, entity);

        // 2. Obtener lista original
        List<ArticuloManufacturadoDetalleEntity> existentes = entity.getArticuloManufacturadoDetalle();
        Map<Long, ArticuloManufacturadoDetalleEntity> existentesMap = existentes.stream()
                .filter(d -> d.getId() != null)
                .collect(Collectors.toMap(ArticuloManufacturadoDetalleEntity::getId, Function.identity()));

        // 3. Agrupar detalles del DTO por articuloInsumoId (sumando cantidades si hay repetidos)
        Map<Long, ArticuloManufacturadoDetalleDto> detallesUnificados = new HashMap<>();

        for (ArticuloManufacturadoDetalleDto dto : updateDto.articuloManufacturadoDetalle()) {
            detallesUnificados.merge(dto.articuloInsumoId(), dto,
                    (existente, nuevo) -> new ArticuloManufacturadoDetalleDto(
                            existente.id() != null ? existente.id() : nuevo.id(),
                            existente.cantidad() + nuevo.cantidad(),
                            existente.articuloInsumoId(),
                            existente.articuloDenominacion()
                    )
            );
        }

        // 4. Construir nueva lista de detalles con objetos reales de insumo
        List<ArticuloManufacturadoDetalleEntity> nuevosDetalles = new ArrayList<>();

        for (ArticuloManufacturadoDetalleDto dto : detallesUnificados.values()) {
            ArticuloManufacturadoDetalleEntity detalle;

            if (dto.id() != null && existentesMap.containsKey(dto.id())) {
                // Actualizar existente
                detalle = existentesMap.get(dto.id());
            } else {
                // Crear nuevo
                detalle = new ArticuloManufacturadoDetalleEntity();
            }

            detalle.setCantidad(dto.cantidad());
            detalle.setArticuloInsumo(insumos.get(dto.articuloInsumoId())); // ✔️ insumo real desde DB
            detalle.setArticuloManufacturado(entity);

            nuevosDetalles.add(detalle);
        }

        // 5. Limpieza de eliminados
        existentes.removeIf(existing ->
                existing.getId() != null &&
                        nuevosDetalles.stream().noneMatch(nuevo ->
                                nuevo.getId() != null && nuevo.getId().equals(existing.getId())
                        )
        );

        // 6. Agregar nuevos si no están
        for (ArticuloManufacturadoDetalleEntity nuevo : nuevosDetalles) {
            if (nuevo.getId() == null || existentes.stream().noneMatch(e ->
                    e.getId() != null && e.getId().equals(nuevo.getId()))) {
                existentes.add(nuevo);
            }
        }

        // 7. Recalcular costo y tiempo estimado (con insumos completos)
        /*entity.costoMinimoCalculado();
        entity.setTiempoEstimadoMinutos(updateDto.tiempoEstimadoMinutos());
         */

        //8. Mapear Imagenes
        entity.getImagenesUrls().forEach(image ->{
            image.setArticulo(entity);
        });
    }

    // ======================= RESPONSE =======================

    @Mapping(target = "categoriaId", source = "categoria.id")
    @Mapping(target = "categoriaDenominacion", source = "categoria.denominacion")
    public abstract ArticuloManufacturadoResponse toResponse(ArticuloManufacturadoEntity entity);

    //-------------- BASIC -------------
    @Mapping(target = "imagenesUrls", ignore = true)
    @Mapping(target = "categoriaId", source = "categoria.id")
    @Mapping(target = "categoriaDenominacion", source = "categoria.denominacion")
    public abstract ArticuloManufacturadoBasicResponse toBasicResponse(ArticuloManufacturadoEntity entity);

    @AfterMapping
    protected void mapImagenes(ArticuloManufacturadoEntity entity,
                               @MappingTarget ArticuloManufacturadoBasicResponse.ArticuloManufacturadoBasicResponseBuilder builder) {
        Set<String> urls = entity.getImagenesUrls().stream()
                .map(ImagenArticuloEntity::getUrl)
                .collect(Collectors.toSet());
        builder.imagenesUrls(urls);
    }

}
