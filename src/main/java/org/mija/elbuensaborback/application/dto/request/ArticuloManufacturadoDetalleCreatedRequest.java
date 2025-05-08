package org.mija.elbuensaborback.application.dto.request;

import lombok.Builder;
import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

@Builder
public record ArticuloManufacturadoDetalleCreatedRequest(
        Double cantidad,
        UnidadMedidaEnum unidadMedidaEnum,
        Long articuloInsumoId
) {
}
