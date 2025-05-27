package org.mija.elbuensaborback.application.mapper;

import org.mapstruct.Mapper;
import org.mija.elbuensaborback.application.dto.response.ClienteBasicResponse;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ClienteEntity;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteBasicResponse toResponse(ClienteEntity entity);
}
