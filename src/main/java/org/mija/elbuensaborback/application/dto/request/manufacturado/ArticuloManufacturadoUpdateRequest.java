package org.mija.elbuensaborback.application.dto.request.manufacturado;

import lombok.Builder;
import org.mija.elbuensaborback.application.dto.global.manufacturado.ArticuloManufacturadoDetalleDto;
import org.mija.elbuensaborback.application.dto.global.manufacturado.ImagenDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Builder
public record ArticuloManufacturadoUpdateRequest(
        String denominacion,
        BigDecimal precioVenta,
        BigDecimal precioCosto,
        Boolean productoActivo,
        Boolean esVendible,
        Integer tiempoEstimadoMinutos,
        Set<ImagenDto> imagenesUrls,
        Long categoriaId,
        String descripcion,
        List<ArticuloManufacturadoDetalleDto> articuloManufacturadoDetalle,
        Float margen
){}