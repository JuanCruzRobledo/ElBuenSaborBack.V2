package org.mija.elbuensaborback.application.mapper;

import org.mapstruct.Mapper;
import org.mija.elbuensaborback.application.dto.response.LocalidadResponse;
import org.mija.elbuensaborback.infrastructure.persistence.entity.LocalidadEntity;

@Mapper(componentModel = "spring")
public interface LocalidadMapper {
    LocalidadResponse toResponse(LocalidadEntity localidadEntity);
}
