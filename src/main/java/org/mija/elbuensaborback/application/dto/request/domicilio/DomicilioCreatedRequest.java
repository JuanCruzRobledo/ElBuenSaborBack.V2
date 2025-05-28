package org.mija.elbuensaborback.application.dto.request.domicilio;

public record DomicilioCreatedRequest(
        String calle,
        Integer numero,
        String codigoPostal,
        Long localidadId
) {}