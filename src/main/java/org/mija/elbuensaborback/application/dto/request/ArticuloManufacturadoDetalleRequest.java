package org.mija.elbuensaborback.application.dto.request;

import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

public record ArticuloManufacturadoDetalleRequest(
        Double cantidad,
        UnidadMedidaEnum unidadMedidaEnum,
        Long articuloInsumoId
) {
}
