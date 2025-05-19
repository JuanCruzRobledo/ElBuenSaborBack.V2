package org.mija.elbuensaborback.application.dto.request.Pedido;

import org.mija.elbuensaborback.domain.enums.EstadoEnum;
import org.mija.elbuensaborback.domain.enums.FormaPagoEnum;
import org.mija.elbuensaborback.domain.enums.TipoEnvioEnum;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

public record PedidoUpdateRequest(
        TipoEnvioEnum tipoEnvioEnum,
        BigDecimal gastosEnvio,
        Long clienteId,
        Long domicilioId,
        // SE VA A INSERTAR CON EL WEB HOOK DE MP
        //FormaPagoEnum formaPagoEnum;
        List<DetallePedidoCreatedRequest> listaDetalle
) {
}
