package org.mija.elbuensaborback.application.dto.response;


import lombok.Builder;

import java.math.BigDecimal;
import java.util.Set;

public record ArticuloInsumoMenuBasicResponse(
        Long id,
        String denominacion,
        BigDecimal precioVenta,
        Boolean productoActivo,
        Set<String> imagenesUrls,
        Long categoriaId,
        String categoriaDenominacion
) {
    @Builder
    public ArticuloInsumoMenuBasicResponse{}
}
