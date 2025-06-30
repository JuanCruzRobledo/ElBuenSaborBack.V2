package org.mija.elbuensaborback.application.dto.request.promocion;

import org.mija.elbuensaborback.application.dto.global.promocion.DetalleDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public record ArticuloPromocionCreatedRequest(
        String denominacion,
        BigDecimal precioVenta,
        Boolean productoActivo,
        Boolean esVendible,
        Integer tiempoEstimadoMinutos,
        Set<String> imagenesUrls,
        Long categoriaId,
        LocalDate fechaDesde,
        LocalDate fechaHasta,
        LocalTime horaDesde,
        LocalTime horaHasta,
        String descripcion,
        List<DetalleDto> promocionDetalle,
        Float margen
) {
}
