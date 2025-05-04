package org.mija.elbuensaborback.application.dto.request;


import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public record ArticuloManufacturadoRequestDto(
        String denominacion,
        BigDecimal precioVenta,
        Boolean productoActivo,
        Set<ImagenArticuloRequestDto> listaImagenes,
        Long categoriaId,
        String categoriaNombre,
        Long sucursalId,
        String sucursalNombre,
        String descripcion,
        BigDecimal precioCosto,
        Integer tiempoEstimadoMinutos,
        Double pesoTotal,
        UnidadMedidaEnum unidadMedidaEnum,
        List<ArticuloManufacturadoDetalleRequestDto> detalle
) {
}
