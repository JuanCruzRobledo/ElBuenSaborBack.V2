package org.mija.elbuensaborback.application.dto.request.manufacturado;

import lombok.Builder;

@Builder
public record ArticuloManufacturadoDetalleCreatedRequest(
        Double cantidad,
        Long articuloInsumoId
) {
}
