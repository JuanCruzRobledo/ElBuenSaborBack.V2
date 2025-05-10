package org.mija.elbuensaborback.application.dto.global;

import lombok.Builder;
import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

@Builder
public record ArticuloManufacturadoDetalleDto(
        Long id,
        Double cantidad,
        UnidadMedidaEnum unidadMedidaEnum,
        Long articuloInsumoId
) {}