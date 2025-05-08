package org.mija.elbuensaborback.application.dto.request;


import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public record ArticuloManufacturadoCreateRequest(
        String denominacion,
        BigDecimal precioVenta,
        String descripcion,
        BigDecimal precioCosto,
        Integer tiempoEstimadoMinutos,
        UnidadMedidaEnum unidadMedidaEnum,
        Long categoriaId,
        Long sucursalId,
        Set<String> imagenesUrls,
        List<ArticuloManufacturadoDetalleRequest> articuloManufacturadoDetalle
) {}
