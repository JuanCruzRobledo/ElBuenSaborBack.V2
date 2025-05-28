package org.mija.elbuensaborback.application.mapper;

import org.mapstruct.Mapper;
import org.mija.elbuensaborback.application.dto.response.ProvinciaResponse;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ProvinciaEntity;

@Mapper(componentModel = "spring", uses = {PaisMapper.class})
public interface ProvinciaMapper {
    ProvinciaResponse toResponse(ProvinciaEntity provinciaEntity);
}
