package org.mija.elbuensaborback.application.dto.request;

import lombok.Builder;
import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

@Builder
public record ArticuloManufacturadoDetalleUpdateRequest(
        Long id,
        Long articuloInsumoId,
        Double cantidad,
        UnidadMedidaEnum unidadMedidaEnum
) {
}
