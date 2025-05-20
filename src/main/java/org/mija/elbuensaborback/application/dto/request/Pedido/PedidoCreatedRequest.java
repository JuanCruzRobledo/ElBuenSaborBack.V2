package org.mija.elbuensaborback.application.dto.request.Pedido;

import org.mija.elbuensaborback.domain.enums.FormaPagoEnum;
import org.mija.elbuensaborback.domain.enums.TipoEnvioEnum;

import java.util.List;

public record PedidoCreatedRequest(
        TipoEnvioEnum tipoEnvioEnum,
        FormaPagoEnum formaPagoEnum,
        Long clienteId,
        Long domicilioId,
        List<DetallePedidoDto> listaDetalle
) {
}
