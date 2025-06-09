package org.mija.elbuensaborback.application.dto.request.cliente;

public record ClienteUpdateRequest(
        String nombre,
        String apellido,
        //String email,
        String telefono
) {
}
