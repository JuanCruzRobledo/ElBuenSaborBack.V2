package org.mija.elbuensaborback.application.dto.request.manufacturado;


import lombok.Builder;
import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Builder
public record ArticuloManufacturadoCreatedRequest(
        String denominacion,
        BigDecimal precioVenta,
        Boolean productoActivo,
        Set<String> imagenesUrls,
        Long categoriaId,
        Long sucursalId,
        String descripcion,
        BigDecimal precioCosto,
        Integer tiempoEstimadoMinutos,
        UnidadMedidaEnum unidadMedidaEnum,
        List<ArticuloManufacturadoDetalleCreatedRequest> articuloManufacturadoDetalle
){}
