package org.mija.elbuensaborback.application.dto.response;

import lombok.Builder;

public record ClienteBasicResponse(
        Long id,
        String nombre,
        String apellido,
        String telefono,
        String email,
        String imagen
) {
    @Builder
    public ClienteBasicResponse{}
}
