package org.mija.elbuensaborback.application.dto.global.manufacturado;

import lombok.Builder;
import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

@Builder
public record ArticuloManufacturadoDetalleDto(
        Long id,
        Double cantidad,
        Long articuloInsumoId,
        String articuloDenominacion
) {}