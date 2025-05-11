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

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ArticuloManufacturadoDetalleMapper.class, ImagenArticuloMapper.class })
public abstract class ArticuloManufacturadoMapper {


    // ======================= CREATE =======================
    @Mapping(target = "imagenesUrls", ignore = true)
    @Mapping(target = "categoria.id", source = "categoriaId")
    @Mapping(target = "sucursal.id", source = "sucursalId")
    @Mapping(target = "pesoTotal", ignore = true)
    public abstract ArticuloManufacturadoEntity toEntity(ArticuloManufacturadoCreatedRequest request);


    @AfterMapping
    protected void mapImagenes(@MappingTarget ArticuloManufacturadoEntity entity, ArticuloManufacturadoCreatedRequest dto) {
        if (dto.imagenesUrls() != null) {
            Set<ImagenArticuloEntity> imagenes = dto.imagenesUrls().stream()
                    .map(url -> {
                        return ImagenArticuloEntity.builder()
                                .denominacion(url)
                                .articulo(entity)
                                .build();
                    }).collect(Collectors.toSet());
            entity.setImagenesUrls(imagenes);
        }
    }

    @AfterMapping
    protected void calcularPeso(@MappingTarget ArticuloManufacturadoEntity entity, ArticuloManufacturadoCreatedRequest dto) {
        entity.pesoTotal();
        entity.getArticuloManufacturadoDetalle().stream().forEach(detalle -> { detalle.setArticuloManufacturado(entity); });
    }


    // ======================= UPDATE =======================

    @AfterMapping
    protected void actualizarPeso(@MappingTarget ArticuloManufacturadoEntity entity, ArticuloManufacturadoUpdateRequest updateDto) {
        entity.pesoTotal();
    }

    @AfterMapping
    protected void relacionConArticulo( ArticuloManufacturadoUpdateRequest updateDto, @MappingTarget ArticuloManufacturadoEntity entity){
        entity.getArticuloManufacturadoDetalle().forEach(detalle -> detalle.setArticuloManufacturado(entity));
    }

    @Mapping(target = "id", ignore = true)// no queremos sobrescribir el ID del entity existente
    @Mapping(target = "articuloManufacturadoDetalle", ignore = true)
    public abstract void updateEntityFromDto(ArticuloManufacturadoUpdateRequest dto, @MappingTarget ArticuloManufacturadoEntity entity);

    public void updateEntityWithDetalles(ArticuloManufacturadoUpdateRequest updateDto, @MappingTarget ArticuloManufacturadoEntity entity) {
        // Mapeo básico de campos
        updateEntityFromDto(updateDto, entity);

        List<ArticuloManufacturadoDetalleEntity> existentes = entity.getArticuloManufacturadoDetalle();

        // Map para acceso rápido por ID
        Map<Long, ArticuloManufacturadoDetalleEntity> existentesMap = existentes.stream()
                .filter(d -> d.getId() != null)
                .collect(Collectors.toMap(ArticuloManufacturadoDetalleEntity::getId, d -> d));

        // Lista final que conservará la referencia original
        List<ArticuloManufacturadoDetalleEntity> nuevosDetalles = new ArrayList<>();

        for (ArticuloManufacturadoDetalleDto dtoDetalle : updateDto.articuloManufacturadoDetalle()) {
            ArticuloManufacturadoDetalleEntity detalle;

            if (dtoDetalle.id() != null && existentesMap.containsKey(dtoDetalle.id())) {
                // Actualizar existente
                detalle = existentesMap.get(dtoDetalle.id());
                detalle.setCantidad(dtoDetalle.cantidad());
                detalle.setUnidadMedidaEnum(dtoDetalle.unidadMedidaEnum());
            } else {
                // Crear nuevo
                detalle = new ArticuloManufacturadoDetalleEntity();
                detalle.setCantidad(dtoDetalle.cantidad());
                detalle.setUnidadMedidaEnum(dtoDetalle.unidadMedidaEnum());
                detalle.setArticuloInsumo(ArticuloInsumoEntity.builder().id(dtoDetalle.articuloInsumoId()).build());
                detalle.setArticuloManufacturado(entity);
            }

            nuevosDetalles.add(detalle);
        }

        // Eliminar los que ya no están (in-place, sin reemplazar la colección)
        existentes.removeIf(existing ->
                existing.getId() != null &&
                        nuevosDetalles.stream().noneMatch(nuevo ->
                                nuevo.getId() != null && nuevo.getId().equals(existing.getId()))
        );

        // Agregar los nuevos que no están ya
        for (ArticuloManufacturadoDetalleEntity nuevo : nuevosDetalles) {
            if (!existentes.contains(nuevo)) {
                existentes.add(nuevo);
            }
        }
    }

    // ======================= RESPONSE =======================

    @Mapping(target = "categoriaId", source = "categoria.id")
    @Mapping(target = "sucursalId", source = "sucursal.id")
    public abstract ArticuloManufacturadoResponse toResponse(ArticuloManufacturadoEntity entity);

    //-------------- BASIC -------------
    public abstract ArticuloManufacturadoBasicResponse toBasicResponse(ArticuloManufacturadoEntity entity);
}
