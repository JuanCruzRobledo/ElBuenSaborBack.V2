package org.mija.elbuensaborback.application.dto.response;

public record ProvinciaResponse(
        Long id,
        String nombre,
        PaisResponse pais
) {
}
