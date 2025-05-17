package org.mija.elbuensaborback.application.dto.response;

import java.math.BigDecimal;

public record ArticuloManufacturadoBasicResponse(
        Long id,
        String denominacion,
        BigDecimal precioVenta,
        //Set<String> imagenesUrls,
        String descripcion,
        Integer tiempoEstimadoMinutos
) {}
