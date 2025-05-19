package org.mija.elbuensaborback.application.dto.response;

import java.time.LocalTime;

public record PedidoResponse(
        Long id,
        LocalTime horaEstimadaFinalizacion
        //FACTURA
) {
}
