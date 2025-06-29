package org.mija.elbuensaborback.application.dto.request.manufacturado;


import lombok.Builder;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Builder
public record ArticuloManufacturadoCreatedRequest(
        String denominacion,
        BigDecimal precioVenta,
        Boolean productoActivo,
        Integer tiempoEstimadoMinutos,
        Set<String> imagenesUrls,
        Long categoriaId,
        String descripcion,
        BigDecimal precioCosto,
        List<ArticuloManufacturadoDetalleCreatedRequest> articuloManufacturadoDetalle,
        Float margen
){}
