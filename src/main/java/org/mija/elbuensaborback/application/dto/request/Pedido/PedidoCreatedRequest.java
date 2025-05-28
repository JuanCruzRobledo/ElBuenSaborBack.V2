package org.mija.elbuensaborback.application.dto.request.Pedido;

import org.mija.elbuensaborback.domain.enums.EstadoEnum;
import org.mija.elbuensaborback.domain.enums.FormaPagoEnum;
import org.mija.elbuensaborback.domain.enums.TipoEnvioEnum;
import org.mija.elbuensaborback.domain.enums.EstadoPagoEnum;

import java.util.List;

public record PedidoCreatedRequest(
        TipoEnvioEnum tipoEnvioEnum,
        FormaPagoEnum formaPagoEnum,
        EstadoPagoEnum estadoPagoEnum,
        EstadoEnum estadoEnum,
        Long clienteId,
        Long domicilioId,
        String indicaciones,
        List<DetallePedidoDto> listaDetalle
) {
}
