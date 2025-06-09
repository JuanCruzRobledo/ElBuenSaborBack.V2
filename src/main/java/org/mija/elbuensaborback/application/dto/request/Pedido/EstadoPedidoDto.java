package org.mija.elbuensaborback.application.dto.request.Pedido;

import org.mija.elbuensaborback.domain.enums.EstadoEnum;
import org.mija.elbuensaborback.domain.enums.EstadoPagoEnum;
import org.mija.elbuensaborback.domain.enums.TipoEnvioEnum;

import java.time.LocalTime;

public record EstadoPedidoDto(
        Long pedidoId,
        EstadoEnum nuevoEstado,
        EstadoPagoEnum estadoPagoEnum,
        LocalTime horaEstimadaFinalizacion,
        TipoEnvioEnum tipoEnvioEnum
) {
}
