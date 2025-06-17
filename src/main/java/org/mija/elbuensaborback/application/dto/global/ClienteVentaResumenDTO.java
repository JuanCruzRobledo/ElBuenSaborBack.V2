package org.mija.elbuensaborback.application.dto.global;

import java.math.BigDecimal;

public record ClienteVentaResumenDTO(
        String nombre,
        String apellido,
        Long cantidadPedidosFinalizados,
        BigDecimal totalPedidosFinalizados
) {}