package org.mija.elbuensaborback.application.dto.response;

import lombok.Builder;
import org.mija.elbuensaborback.application.dto.global.promocion.DetalleDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public record ArticuloPromocionMenuBasicResponse(
        Long id,
        String denominacion,
        BigDecimal precioVenta,
        BigDecimal precioTotal,
        Boolean productoActivo,
        Set<String> imagenesUrls,
        Long categoriaId,
        String categoriaDenominacion,
        LocalDate fechaDesde,
        LocalDate fechaHasta,
        LocalTime horaDesde,
        LocalTime horaHasta,
        String descripcion
) {
    @Builder
    public ArticuloPromocionMenuBasicResponse{}
}
