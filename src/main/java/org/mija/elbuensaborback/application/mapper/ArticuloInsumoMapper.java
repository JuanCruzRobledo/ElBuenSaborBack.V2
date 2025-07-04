package org.mija.elbuensaborback.application.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mija.elbuensaborback.application.dto.global.manufacturado.ImagenDto;
import org.mija.elbuensaborback.application.dto.request.insumo.ArticuloInsumoCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.insumo.ArticuloInsumoUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ArticuloInsumoBasicResponse;
import org.mija.elbuensaborback.application.dto.response.ArticuloInsumoMenuBasicResponse;
import org.mija.elbuensaborback.application.dto.response.ArticuloInsumoResponse;
import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoBasicResponse;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloInsumoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloManufacturadoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ImagenArticuloEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ImagenArticuloMapper.class})
public abstract class ArticuloInsumoMapper {

    @Mapping(target = "categoriaId", source = "categoria.id")
    @Mapping(target = "categoriaDenominacion", source = "categoria.denominacion")
    public abstract ArticuloInsumoResponse toResponse(ArticuloInsumoEntity articuloEntity);

    @Mapping(target = "imagenesUrls", ignore = true)
    @Mapping(target = "categoria.id", source = "categoriaId")
    public abstract ArticuloInsumoEntity toEntity(ArticuloInsumoCreatedRequest articuloCreatedRequest);

    //Despues usar en mapper imagen
    @AfterMapping
    protected void mapImagenes(@MappingTarget ArticuloInsumoEntity entity, ArticuloInsumoCreatedRequest articuloCreatedRequest) {
        if (articuloCreatedRequest.imagenesUrls() != null) {
            Set<ImagenArticuloEntity> imagenes = articuloCreatedRequest.imagenesUrls().stream()
                    .map(url -> {
                        return ImagenArticuloEntity.builder()
                                .url(url)
                                .articulo(entity)
                                .build();
                    }).collect(Collectors.toSet());
            entity.setImagenesUrls(imagenes);
        }
    }


    @Mapping(target = "id", ignore = true )
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "imagenesUrls" , ignore = true)
    public abstract void toEntity(@MappingTarget ArticuloInsumoEntity articuloEntity,ArticuloInsumoUpdateRequest articuloUpdatedRequest);

    public void updateEntity(@MappingTarget ArticuloInsumoEntity articuloEntity, ArticuloInsumoUpdateRequest articuloUpdateRequest) {
        toEntity(articuloEntity, articuloUpdateRequest);

        Set<ImagenArticuloEntity> imagenesActuales = articuloEntity.getImagenesUrls();

        // Eliminar imágenes que ya no están en el DTO
        Set<Long> idsDelDto = articuloUpdateRequest.imagenesUrls().stream()
                .filter(dto -> dto.id() != null)
                .map(ImagenDto::id)
                .collect(Collectors.toSet());

        imagenesActuales.removeIf(imagen -> imagen.getId() != null && !idsDelDto.contains(imagen.getId()));

        // Agregar nuevas imágenes (las que tienen id == null)
        articuloUpdateRequest.imagenesUrls().stream()
                .filter(dto -> dto.id() == null)
                .forEach(dto -> {
                    ImagenArticuloEntity nueva = ImagenArticuloEntity.builder()
                            .url(dto.url())
                            .articulo(articuloEntity)
                            .build();
                    imagenesActuales.add(nueva);
                });
    }

    @Mapping(target = "categoriaDenominacion", source = "categoria.denominacion")
    public abstract ArticuloInsumoBasicResponse toBasic(ArticuloInsumoEntity articuloEntity);


    @Mapping(target = "categoriaId", source = "categoria.id")
    @Mapping(target = "categoriaDenominacion", source = "categoria.denominacion")
    @Mapping(target = "imagenesUrls", ignore = true)
    @Mapping(target = "categoriaPadre", source = "categoria.categoriaPadre.denominacion")
    public abstract ArticuloInsumoMenuBasicResponse menuBasicResponse(ArticuloInsumoEntity articuloEntity);

    //Despues usar en mapper imagen
    @AfterMapping
    protected void mapImagenes(@MappingTarget ArticuloInsumoMenuBasicResponse.ArticuloInsumoMenuBasicResponseBuilder builder, ArticuloInsumoEntity articuloEntity) {
        if (articuloEntity.getImagenesUrls() != null) {
            Set<String> imagenes = articuloEntity.getImagenesUrls().stream()
                    .map(ImagenArticuloEntity::getUrl).collect(Collectors.toSet());
            builder.imagenesUrls(imagenes);
        }
    }

}
