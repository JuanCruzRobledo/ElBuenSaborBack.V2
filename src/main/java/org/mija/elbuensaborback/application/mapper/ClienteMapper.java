package org.mija.elbuensaborback.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mija.elbuensaborback.application.dto.request.cliente.ClienteUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ClienteBasicResponse;
import org.mija.elbuensaborback.application.dto.response.ClienteResponse;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ClienteEntity;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, DomicilioMapper.class, ImagenArticuloMapper.class})
public interface ClienteMapper {

    @Mapping(target = "email",source = "usuario.email")
    ClienteBasicResponse toBasicResponse(ClienteEntity entity);

    ClienteResponse toResponse(ClienteEntity entity);

    ClienteEntity toEntity(ClienteUpdateRequest clienteUpdateRequest);

    @Mapping(target = "usuario", ignore = true)
    void actualizarDesdeDto(ClienteUpdateRequest dto, @MappingTarget ClienteEntity entity);
}
