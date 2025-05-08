package org.mija.elbuensaborback.application.dto.response;

import lombok.Builder;

@Builder
public record ImagenResponse (
        Long id,
        String denominacion
){
}
