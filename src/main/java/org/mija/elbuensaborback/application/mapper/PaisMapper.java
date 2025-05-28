package org.mija.elbuensaborback.application.mapper;

import org.mapstruct.Mapper;
import org.mija.elbuensaborback.application.dto.response.PaisResponse;
import org.mija.elbuensaborback.infrastructure.persistence.entity.PaisEntity;

@Mapper(componentModel = "spring")
public interface PaisMapper {

    PaisResponse toResponse(PaisEntity paisEntity);
}
