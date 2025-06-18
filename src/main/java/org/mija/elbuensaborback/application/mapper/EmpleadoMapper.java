package org.mija.elbuensaborback.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mija.elbuensaborback.application.dto.request.empleado.EmpleadoCreatedRequest;
import org.mija.elbuensaborback.application.dto.response.EmpleadoResponse;
import org.mija.elbuensaborback.infrastructure.persistence.entity.EmpleadoEntity;

@Mapper(componentModel = "spring", uses = { RolMapper.class})
public interface EmpleadoMapper {
    @Mapping(target = "email", source = "usuario.email")
    @Mapping(target = "rol", source = "usuario.rol")
    EmpleadoResponse toResponse(EmpleadoEntity entity);

    @Mapping(target = "usuario.email", source = "email")
    @Mapping(target = "usuario.password", source = "password")
    EmpleadoEntity toEntity(EmpleadoCreatedRequest request);

}
