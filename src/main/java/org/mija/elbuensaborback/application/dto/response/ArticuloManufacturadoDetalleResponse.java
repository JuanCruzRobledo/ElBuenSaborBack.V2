package org.mija.elbuensaborback.application.dto.response;

import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

public record ArticuloManufacturadoDetalleResponse(
        Long id,
        Long articuloInsumoId,
        String articuloInsumoDenominacion,
        Double cantidad,
        UnidadMedidaEnum unidadMedidaEnum
) {}