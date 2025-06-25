package org.mija.elbuensaborback.application.dto.request.cliente;

public record ClienteBasicUpdateRequest(
        String nombre,
        String apellido,
        //String email,
        String telefono
) {
}
