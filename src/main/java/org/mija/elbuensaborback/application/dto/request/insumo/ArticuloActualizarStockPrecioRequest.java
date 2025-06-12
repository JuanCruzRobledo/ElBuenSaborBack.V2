package org.mija.elbuensaborback.application.dto.request.insumo;

import java.math.BigDecimal;

public record ArticuloActualizarStockPrecioRequest(
        BigDecimal precioCosto,
        Double stockActual
) {}
