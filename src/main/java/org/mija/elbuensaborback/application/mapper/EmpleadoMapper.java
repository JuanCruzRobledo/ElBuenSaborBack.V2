package org.mija.elbuensaborback.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mija.elbuensaborback.application.dto.request.cliente.ClienteCompleteUpdateRequest;
import org.mija.elbuensaborback.application.dto.request.empleado.EmpleadoBasicUpdateRequest;
import org.mija.elbuensaborback.application.dto.request.empleado.EmpleadoCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.empleado.EmpleadoUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ClienteBasicResponse;
import org.mija.elbuensaborback.application.dto.response.EmpleadoBasicResponse;
import org.mija.elbuensaborback.application.dto.response.EmpleadoResponse;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ClienteEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.EmpleadoEntity;

@Mapper(componentModel = "spring", uses = { RolMapper.class})
public interface EmpleadoMapper {
    @Mapping(target = "email", source = "usuario.email")
    @Mapping(target = "rol", source = "usuario.rol")
    EmpleadoResponse toResponse(EmpleadoEntity entity);

    @Mapping(target = "usuario.email", source = "email")
    @Mapping(target = "usuario.password", source = "password")
    EmpleadoEntity toEntity(EmpleadoCreatedRequest request);

    @Mapping(target = "email",source = "usuario.email")
    @Mapping(target = "rol", source = "usuario.rol.rolEnum")
    EmpleadoBasicResponse toBasicResponse(EmpleadoEntity entity);

    @Mapping(target = "usuario", ignore = true)
    void actualizarDesdeDto(EmpleadoUpdateRequest dto, @MappingTarget EmpleadoEntity entity);

    @Mapping(target = "usuario", ignore = true)
    void actualizarDesdeDto(EmpleadoBasicUpdateRequest dto, @MappingTarget EmpleadoEntity entity);

}
