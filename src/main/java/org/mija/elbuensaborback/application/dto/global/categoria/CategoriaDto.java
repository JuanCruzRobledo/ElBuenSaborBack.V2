package org.mija.elbuensaborback.application.dto.global.categoria;

public record CategoriaDto(
        Long id,
        String denominacion,
        Long categoriaPadre
) {
}
