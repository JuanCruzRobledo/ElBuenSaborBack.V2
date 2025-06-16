package org.mija.elbuensaborback.application.dto.response;

import lombok.Builder;
import org.mija.elbuensaborback.application.dto.global.manufacturado.ArticuloManufacturadoDetalleDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public record ArticuloManufacturadoBasicResponse(
        Long id,
        String denominacion,
        String descripcion,
        Long categoriaId,
        String categoriaDenominacion,
        BigDecimal precioVenta,
        Set<String> imagenesUrls,
        //Integer tiempoEstimadoMinutos,
        boolean productoActivo
        //List<ArticuloManufacturadoDetalleDto> articuloManufacturadoDetalle
) {
    @Builder
    public ArticuloManufacturadoBasicResponse {}
}
