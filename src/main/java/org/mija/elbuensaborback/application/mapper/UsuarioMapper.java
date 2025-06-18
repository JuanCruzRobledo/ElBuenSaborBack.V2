package org.mija.elbuensaborback.application.mapper;

import org.mapstruct.Mapper;
import org.mija.elbuensaborback.application.dto.response.UsuarioResponse;
import org.mija.elbuensaborback.infrastructure.persistence.entity.UsuarioEntity;

@Mapper(componentModel = "spring", uses = {RolMapper.class})
public interface UsuarioMapper {


    UsuarioResponse toResponse(UsuarioEntity entity);
}
