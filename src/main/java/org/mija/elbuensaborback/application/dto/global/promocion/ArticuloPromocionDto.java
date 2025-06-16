package org.mija.elbuensaborback.application.dto.global.promocion;

import org.mija.elbuensaborback.application.dto.global.manufacturado.ImagenDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public record ArticuloPromocionDto(
        Long id,
        String denominacion,
        BigDecimal precioVenta,
        Boolean productoActivo,
        Boolean esVendible,
        Integer tiempoEstimadoMinutos,
        BigDecimal precioCosto,
        Set<ImagenDto> imagenesUrls,
        Long categoriaId,
        String categoriaDenominacion,
        LocalDate fechaDesde,
        LocalDate fechaHasta,
        LocalTime horaDesde,
        LocalTime horaHasta,
        String descripcion,
        BigDecimal precioTotal,
        List<DetalleDto> promocionDetalle
) {
}
