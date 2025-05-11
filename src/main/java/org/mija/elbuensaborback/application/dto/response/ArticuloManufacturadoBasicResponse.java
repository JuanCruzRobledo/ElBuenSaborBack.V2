package org.mija.elbuensaborback.application.dto.response;

import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

import java.math.BigDecimal;
import java.util.Set;

public record ArticuloManufacturadoBasicResponse(
        Long id,
        String denominacion,
        BigDecimal precioVenta,
        //Set<String> imagenesUrls,
        String descripcion,
        Integer tiempoEstimadoMinutos,
        Double pesoTotal,
        UnidadMedidaEnum unidadMedidaEnum
) {}
