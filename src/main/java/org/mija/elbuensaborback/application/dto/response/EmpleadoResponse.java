package org.mija.elbuensaborback.application.dto.response;

import org.mija.elbuensaborback.application.dto.global.rol.RolDto;

public record EmpleadoResponse(
        Long id,
        String nombre,
        String apellido,
        String telefono,
        String email,
        boolean activo,
        RolDto rol
) {
}
