package org.mija.elbuensaborback.application.dto.response;

public record LocalidadResponse(
        Long id,
        String nombre,
        ProvinciaResponse provincia
) {
}
