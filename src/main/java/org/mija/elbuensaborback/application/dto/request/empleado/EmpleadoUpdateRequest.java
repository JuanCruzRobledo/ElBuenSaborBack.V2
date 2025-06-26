package org.mija.elbuensaborback.application.dto.request.empleado;

public record EmpleadoUpdateRequest(
        String nombre,
        String apellido,
        String telefono,
        boolean activo,
        String email,
        Long rolId
) {
}
