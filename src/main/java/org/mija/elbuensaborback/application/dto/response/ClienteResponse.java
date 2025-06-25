package org.mija.elbuensaborback.application.dto.response;


import org.mija.elbuensaborback.application.dto.global.manufacturado.ImagenDto;

import java.util.List;

public record ClienteResponse(
        Long id,
        String nombre,
        String apellido,
        String telefono,
        //UsuarioResponse usuario,
        String email,
        boolean activo,
        List<DomicilioResponse> domicilio,
        ImagenDto imagen
) {
}
