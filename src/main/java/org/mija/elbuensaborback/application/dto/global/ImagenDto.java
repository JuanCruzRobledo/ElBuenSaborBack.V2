package org.mija.elbuensaborback.application.dto.global;

import lombok.Builder;

@Builder
public record ImagenDto (
        Long id,
        String denominacion
){
}
