package org.mija.elbuensaborback.application.dto.response;

import java.time.LocalTime;

public record PedidoBasicResponse(
        Long id,
        LocalTime horaEstimadaFinalizacion
) {
}
