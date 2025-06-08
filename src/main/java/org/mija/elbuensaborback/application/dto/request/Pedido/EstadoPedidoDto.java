package org.mija.elbuensaborback.application.dto.request.Pedido;

import org.mija.elbuensaborback.domain.enums.EstadoEnum;

public record EstadoPedidoDto(
        Long pedidoId,
        EstadoEnum nuevoEstado
) {
}
