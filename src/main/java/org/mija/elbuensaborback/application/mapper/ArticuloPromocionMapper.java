package org.mija.elbuensaborback.application.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mija.elbuensaborback.application.dto.global.promocion.ArticuloPromocionDto;
import org.mija.elbuensaborback.application.dto.request.promocion.ArticuloPromocionCreatedRequest;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloPromocionEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ImagenArticuloEntity;

import java.util.Set;
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
}
