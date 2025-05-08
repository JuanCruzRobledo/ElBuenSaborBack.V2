package org.mija.elbuensaborback.application.dto.response;

import java.math.BigDecimal;
import java.util.Set;

public record ArticuloResponse(
        Long id,
        String denominacion,
        BigDecimal precioVenta,
        Boolean productoActivo,
        Set<String> imagenesUrls,
        String tipoArticulo // "MANUFACTURADO" o "INSUMO"
) {}