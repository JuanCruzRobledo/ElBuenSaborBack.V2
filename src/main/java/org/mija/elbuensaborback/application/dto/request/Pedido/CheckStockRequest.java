package org.mija.elbuensaborback.application.dto.request.Pedido;

import java.util.List;

public record CheckStockRequest(
        List<DetallePedidoDto> listaDetalle
) {
}
