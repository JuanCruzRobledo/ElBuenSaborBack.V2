package org.mija.elbuensaborback.application.dto.request.empleado;

public record EmpleadoCreatedRequest(
        String nombre,
        String apellido,
        String telefono,
        String email,
        String password,
        Long rolId
) {
}
