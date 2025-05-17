package org.mija.elbuensaborback.application.dto.global.manufacturado;

import lombok.Builder;

@Builder
public record ImagenDto (
        Long id,
        String url
){
}
