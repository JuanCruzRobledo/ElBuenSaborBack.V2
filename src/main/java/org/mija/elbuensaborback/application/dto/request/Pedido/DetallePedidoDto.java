package org.mija.elbuensaborback.application.dto.request.Pedido;

import java.math.BigDecimal;

public record DetallePedidoDto(
        Long id,
        Integer cantidad,
        BigDecimal subTotal,
        Long articuloId
) {
}
