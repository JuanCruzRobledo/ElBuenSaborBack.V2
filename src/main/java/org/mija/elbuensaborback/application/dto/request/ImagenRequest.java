package org.mija.elbuensaborback.application.dto.request;

import lombok.Builder;

@Builder
public record ImagenRequest(
        Long id,
        String denominacion
) {
}
