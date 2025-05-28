package org.mija.elbuensaborback.application.dto.request.domicilio;

public record DomicilioUpdateRequest(
        String calle,
        Integer numero,
        String codigoPostal,
        Long localidadId
) {}