package org.mija.elbuensaborback.application.dto.response;

import lombok.Builder;
import org.mija.elbuensaborback.application.dto.global.manufacturado.ArticuloManufacturadoDetalleDto;
import org.mija.elbuensaborback.application.dto.global.manufacturado.ImagenDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Builder
public record ArticuloManufacturadoResponse(
        Long id,
        String denominacion,
        BigDecimal precioVenta,
        Boolean productoActivo,
        Boolean esVendible,
        Integer tiempoEstimadoMinutos,
        Set<ImagenDto> imagenesUrls,
        Long categoriaId,
        String categoriaDenominacion,
        String descripcion,
        BigDecimal precioCosto,
        List<ArticuloManufacturadoDetalleDto> articuloManufacturadoDetalle
) {}