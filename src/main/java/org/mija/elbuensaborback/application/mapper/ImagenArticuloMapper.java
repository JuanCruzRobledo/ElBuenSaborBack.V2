package org.mija.elbuensaborback.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mija.elbuensaborback.application.dto.global.ImagenDto;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ImagenArticuloEntity;


@Mapper(componentModel="spring")
public interface ImagenArticuloMapper {

    @Mapping(target = "articulo",ignore = true)
    ImagenArticuloEntity dtoToEntity(ImagenDto imagenDto);

    ImagenDto entityToDto(ImagenArticuloEntity imagenArticuloEntity);
}
