package org.mija.elbuensaborback.application.dto.request.promocion;

import org.mija.elbuensaborback.application.dto.global.manufacturado.ImagenDto;
import org.mija.elbuensaborback.application.dto.global.promocion.DetalleDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public record ArticuloPromocionUpdateRequest(
        String denominacion,
        BigDecimal precioVenta,
        Boolean productoActivo,
        Integer tiempoEstimadoMinutos,
        BigDecimal precioCosto,
        Set<ImagenDto> imagenesUrls,
        Long categoriaId,
        LocalDate fechaDesde,
        LocalDate fechaHasta,
        LocalTime horaDesde,
        LocalTime horaHasta,
        String descripcionDescuento,
        BigDecimal precioPromocional,
        List<DetalleDto> promocionDetalle
) {
}
