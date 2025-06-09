package org.mija.elbuensaborback.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mija.elbuensaborback.application.dto.request.domicilio.DomicilioCreatedRequest;
import org.mija.elbuensaborback.application.dto.response.DomicilioResponse;
import org.mija.elbuensaborback.infrastructure.persistence.entity.DomicilioEntity;

@Mapper(componentModel = "spring", uses = {LocalidadMapper.class})
public interface DomicilioMapper {

    DomicilioResponse toResponse(DomicilioEntity domicilioEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "localidad", ignore = true)
    DomicilioEntity fromCreatedRequest(DomicilioCreatedRequest domicilioCreatedRequest);

}
