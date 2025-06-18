package org.mija.elbuensaborback.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mija.elbuensaborback.application.dto.request.cliente.ClienteUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ClienteBasicResponse;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ClienteEntity;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    @Mapping(target = "email",source = "usuario.email")
    ClienteBasicResponse toResponse(ClienteEntity entity);

    ClienteEntity toEntity(ClienteUpdateRequest clienteUpdateRequest);

    @Mapping(target = "usuario", ignore = true)
    void actualizarDesdeDto(ClienteUpdateRequest dto, @MappingTarget ClienteEntity entity);
}
