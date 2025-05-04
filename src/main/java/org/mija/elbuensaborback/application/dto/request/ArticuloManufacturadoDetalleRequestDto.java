package org.mija.elbuensaborback.application.dto.request;

import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

public record ArticuloManufacturadoDetalleRequestDto(
        Double cantidad,
        UnidadMedidaEnum unidadMedidaEnum,
        Long articuloInsumoId,
        String articuloInsumoNombre
) {
}
