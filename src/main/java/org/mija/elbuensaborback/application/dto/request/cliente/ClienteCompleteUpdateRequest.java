package org.mija.elbuensaborback.application.dto.request.cliente;

public record ClienteCompleteUpdateRequest(
        String nombre,
        String apellido,
        String telefono,
        String email,
        boolean activo
) {
}
