package org.mija.elbuensaborback.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mija.elbuensaborback.application.dto.global.rol.RolDto;
import org.mija.elbuensaborback.infrastructure.persistence.entity.RoleEntity;

@Mapper(componentModel = "spring")
public interface RolMapper {
    @Mapping(target = "rolName", source = "rolEnum")
    //@Mapping(target = "rolName", ignore = true)
    RolDto toDto(RoleEntity roleEntity);

    @Mapping(target = "rolEnum", source = "rolName")
    //@Mapping(target = "rolEnum", ignore = true)
    RoleEntity toEntity(RolDto rolDto);
}
