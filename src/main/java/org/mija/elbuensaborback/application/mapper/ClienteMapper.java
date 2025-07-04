package org.mija.elbuensaborback.application.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mija.elbuensaborback.application.dto.request.cliente.ClienteBasicUpdateRequest;
import org.mija.elbuensaborback.application.dto.request.cliente.ClienteCompleteUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ClienteBasicResponse;
import org.mija.elbuensaborback.application.dto.response.ClienteResponse;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ClienteEntity;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, DomicilioMapper.class, ImagenArticuloMapper.class})
public interface ClienteMapper {

    @Mapping(target = "email",source = "usuario.email")
    @Mapping(target = "imagen", ignore = true)
    ClienteBasicResponse toBasicResponse(ClienteEntity entity);

    @AfterMapping
    default void setImagenIfNotNull(ClienteEntity entity, @MappingTarget ClienteBasicResponse.ClienteBasicResponseBuilder response) {
        if (entity.getImagen() != null && entity.getImagen().getUrl() != null) {
            response.imagen(entity.getImagen().getUrl());
        }
    }

    @Mapping(target = "email", source = "usuario.email")
    ClienteResponse toResponse(ClienteEntity entity);

    ClienteEntity toEntity(ClienteBasicUpdateRequest clienteBasicUpdateRequest);

    @Mapping(target = "usuario", ignore = true)
    void actualizarDesdeDto(ClienteBasicUpdateRequest dto, @MappingTarget ClienteEntity entity);

    @Mapping(target = "usuario", ignore = true)
    //@Mapping(target = "usuario.email", source = "email")
    void actualizarDesdeDto(ClienteCompleteUpdateRequest dto, @MappingTarget ClienteEntity entity);

}
