package org.mija.elbuensaborback.application.dto.response;

import java.math.BigDecimal;

public record RankingClientesResponse(
        String nombre,
        String apellido,
        Long cantidadPedidosFinalizados,
        BigDecimal totalPedidosFinalizados
) {
}
