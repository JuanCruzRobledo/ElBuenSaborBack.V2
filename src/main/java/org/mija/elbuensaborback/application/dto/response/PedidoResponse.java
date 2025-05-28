package org.mija.elbuensaborback.application.dto.response;

import org.mija.elbuensaborback.application.dto.request.Pedido.DetallePedidoDto;
import org.mija.elbuensaborback.domain.enums.EstadoEnum;
import org.mija.elbuensaborback.domain.enums.EstadoPagoEnum;
import org.mija.elbuensaborback.domain.enums.FormaPagoEnum;
import org.mija.elbuensaborback.domain.enums.TipoEnvioEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record PedidoResponse(
        Long id,
        Integer tiempoEstimadoFinalizacion,
        BigDecimal total,
        BigDecimal gastosEnvio,
        EstadoEnum estadoEnum,
        EstadoPagoEnum estadoPagoEnum,
        TipoEnvioEnum tipoEnvioEnum,
        FormaPagoEnum formaPagoEnum,
        LocalDate fechaPedido,
        Long clienteId,
        String nombreCompleto,
        Long domicilioId,
        String indicaciones,
        List<DetallePedidoDto> listaDetalle
        //FACTURA
) {
}
