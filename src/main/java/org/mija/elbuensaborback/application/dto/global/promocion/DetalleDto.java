package org.mija.elbuensaborback.application.dto.global.promocion;

public record DetalleDto(
        Long id,
        Integer cantidad,
        Long articuloId,
        String articuloDenominacion
) {
}
