package org.mija.elbuensaborback.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mija.elbuensaborback.application.dto.global.manufacturado.ImagenDto;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ImagenArticuloEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ImagenClienteEntity;


@Mapper(componentModel="spring")
public interface ImagenArticuloMapper {

    @Mapping(target = "articulo",ignore = true)
    ImagenArticuloEntity dtoToEntity(ImagenDto imagenDto);

    ImagenDto entityToDto(ImagenArticuloEntity imagenArticuloEntity);

    ImagenDto entityToDto(ImagenClienteEntity imagenClienteEntity);

    /* TENGO QUE ARREGLAR PARA QUE SE HAGA ACA
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "denominacion", ignore = true)
    @Mapping(target = "articulo", ignore = true)
    public abstract Set<ImagenArticuloEntity> stringToImagenEntity(Set<String> imagenDto);


    @AfterMapping
    protected void stringToEntity(@MappingTarget ImagenArticuloEntity imagenArticuloEntity, Set<String> imagenDto ){
        imagenDto.stream()
                .map(url -> {
                    return ImagenArticuloEntity.builder()
                            .denominacion(url)
                            .build();
                }).collect(Collectors.toSet());
    }*/

}
