package org.mija.elbuensaborback.application.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mija.elbuensaborback.application.dto.global.manufacturado.ArticuloManufacturadoDetalleDto;
import org.mija.elbuensaborback.application.dto.global.promocion.ArticuloPromocionDto;
import org.mija.elbuensaborback.application.dto.global.promocion.DetalleDto;
import org.mija.elbuensaborback.application.dto.request.manufacturado.ArticuloManufacturadoUpdateRequest;
import org.mija.elbuensaborback.application.dto.request.promocion.ArticuloPromocionCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.promocion.ArticuloPromocionUpdateRequest;
import org.mija.elbuensaborback.infrastructure.persistence.entity.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {PromocionDetalleMapper.class})
public abstract class ArticuloPromocionMapper {
    @Mapping(target = "categoria.id", source = "categoriaId")
    public abstract ArticuloPromocionEntity toEntity(ArticuloPromocionDto articuloPromocionDto);

    @Mapping(target = "categoriaId" , source = "categoria.id")
    @Mapping(target = "categoriaDenominacion" , source = "categoria.denominacion")
    public abstract ArticuloPromocionDto toResponse(ArticuloPromocionEntity articuloPromocionEntity);

    @Mapping(target = "imagenesUrls", ignore = true)
    @Mapping(target = "categoria.id", source = "categoriaId")
    public abstract ArticuloPromocionEntity toEntity(ArticuloPromocionCreatedRequest articuloPromocionCreatedRequest);

    @AfterMapping
    protected void mapImagenes(@MappingTarget ArticuloPromocionEntity entity, ArticuloPromocionCreatedRequest dto) {
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
    protected void relacionConManufacturado(@MappingTarget ArticuloPromocionEntity entity, ArticuloPromocionCreatedRequest dto) {
        entity.getPromocionDetalle().stream().forEach(detalle -> { detalle.setArticuloPromocion(entity); });
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "promocionDetalle", ignore = true)
    public abstract void updateEntityFromDto( @MappingTarget ArticuloPromocionEntity entity, ArticuloPromocionUpdateRequest dto);


    public void updateEntityWithDetalles(
            ArticuloPromocionUpdateRequest updateDto,
            @MappingTarget ArticuloPromocionEntity entity,
            Map<Long, ArticuloEntity> articulosDisponibles
    ) {
        // 1. Mapear campos básicos (excepto detalles)
        updateEntityFromDto(entity, updateDto);

        // 2. Obtener detalles existentes
        List<PromocionDetalleEntity> existentes = entity.getPromocionDetalle();
        Map<Long, PromocionDetalleEntity> existentesMap = existentes.stream()
                .filter(e -> e.getId() != null)
                .collect(Collectors.toMap(PromocionDetalleEntity::getId, Function.identity()));

        // 3. Agrupar por artículo ID (sumando cantidades si hay repetidos)
        Map<Long, DetalleDto> detallesUnificados = new HashMap<>();
        for (DetalleDto dto : updateDto.promocionDetalle()) {
            detallesUnificados.merge(dto.articuloId(), dto,
                    (existente, nuevo) -> new DetalleDto(
                            existente.id() != null ? existente.id() : nuevo.id(),
                            existente.cantidad() + nuevo.cantidad(),
                            existente.articuloId(),
                            existente.articuloDenominacion()
                    )
            );
        }

        // 4. Construir nueva lista de detalles
        List<PromocionDetalleEntity> nuevosDetalles = new ArrayList<>();
        for (DetalleDto dto : detallesUnificados.values()) {
            PromocionDetalleEntity detalle;

            if (dto.id() != null && existentesMap.containsKey(dto.id())) {
                detalle = existentesMap.get(dto.id());
            } else {
                detalle = new PromocionDetalleEntity();
            }

            detalle.setCantidad(dto.cantidad());
            detalle.setArticulo(articulosDisponibles.get(dto.articuloId()));
            detalle.setArticuloPromocion(entity);

            nuevosDetalles.add(detalle);
        }

        // 5. Eliminar los que ya no están
        existentes.removeIf(existing ->
                existing.getId() != null &&
                        nuevosDetalles.stream().noneMatch(nuevo ->
                                nuevo.getId() != null && nuevo.getId().equals(existing.getId())
                        )
        );

        // 6. Agregar nuevos
        for (PromocionDetalleEntity nuevo : nuevosDetalles) {
            if (nuevo.getId() == null || existentes.stream().noneMatch(e ->
                    e.getId() != null && e.getId().equals(nuevo.getId()))) {
                existentes.add(nuevo);
            }
        }

        // 7. Setear imágenes
        if (entity.getImagenesUrls() != null) {
            entity.getImagenesUrls().forEach(imagen -> imagen.setArticulo(entity));
        }
    }
}
