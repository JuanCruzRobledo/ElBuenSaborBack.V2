package org.mija.elbuensaborback.application.dto.request.Pedido;

import java.math.BigDecimal;

public record DetallePedidoCreatedRequest(
        Integer cantidad,
        BigDecimal subtotal,
        Long idArticulo
) {
}
