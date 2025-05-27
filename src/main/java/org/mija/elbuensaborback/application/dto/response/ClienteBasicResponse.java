package org.mija.elbuensaborback.application.dto.response;

public record ClienteBasicResponse(
        Long id,
        String nombre,
        String apellido,
        String telefono
) {
}
