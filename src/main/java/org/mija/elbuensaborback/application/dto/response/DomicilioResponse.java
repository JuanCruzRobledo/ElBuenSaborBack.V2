package org.mija.elbuensaborback.application.dto.response;

public record DomicilioResponse(
        Long id,
        String calle,
        Integer numero,
        String codigoPostal,
        String descripcion,
        LocalidadResponse localidad
) {
}
