package org.mija.elbuensaborback.application.dto.response;

import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

public record ArticuloManufacturadoDetalleResponseDto(
        Long id,
        Double cantidad,
        UnidadMedidaEnum unidadMedidaEnum,
        Long articuloInsumoId,
        String articuloInsumoNombre
) {}