package org.mija.elbuensaborback.application.dto.request.empleado;

public record EmpleadoBasicUpdateRequest(
        String nombre,
        String apellido,
        String telefono
) {
}
