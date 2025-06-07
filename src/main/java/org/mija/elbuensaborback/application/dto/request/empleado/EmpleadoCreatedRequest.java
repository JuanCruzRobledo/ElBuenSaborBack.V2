package org.mija.elbuensaborback.application.dto.request.empleado;

import org.mija.elbuensaborback.application.dto.global.rol.RolDto;

public record EmpleadoCreatedRequest(
        String nombre,
        String apellido,
        String telefono,
        String email,
        String password,
        RolDto rol
) {
}
