package org.mija.elbuensaborback.application.dto.request.domicilio;

public record DomicilioCreatedRequest(
        String calle,
        Integer numero,
        String codigoPostal,
        String descripcion,
        Long localidadId
) {}