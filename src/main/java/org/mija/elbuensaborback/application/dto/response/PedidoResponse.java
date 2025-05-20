package org.mija.elbuensaborback.application.dto.response;

import org.mija.elbuensaborback.application.dto.request.Pedido.DetallePedidoDto;
import org.mija.elbuensaborback.domain.enums.EstadoEnum;
import org.mija.elbuensaborback.domain.enums.FormaPagoEnum;
import org.mija.elbuensaborback.domain.enums.TipoEnvioEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record PedidoResponse(
        Long id,
        LocalTime horaEstimadaFinalizacion,
        BigDecimal total,
        BigDecimal gastosEnvio,
        EstadoEnum estadoEnum,
        TipoEnvioEnum tipoEnvioEnum,
        FormaPagoEnum formaPagoEnum,
        LocalDate fechaPedido,
        Long clienteId,
        Long domicilioId,
        List<DetallePedidoDto> listaDetalle
        //FACTURA
) {
}
