package org.mija.elbuensaborback.application.dto.request.Pedido;

import java.util.List;

public record PedidoCreatedRequest(
        Long clienteId,
        Long domicilioId,
        List<DetallePedidoCreatedRequest> listaDetalle
) {
}
