package org.mija.elbuensaborback.application.dto.response;

import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public record ArticuloManufacturadoResponseDto(
        Long id,
        String denominacion,
        BigDecimal precioVenta,
        Boolean productoActivo,
        Set<ImagenArticuloResponseDto> listaImagenes,
        Long categoriaId,
        String categoriaNombre,
        Long sucursalId,
        String sucursalNombre,
        String descripcion,
        BigDecimal precioCosto,
        Integer tiempoEstimadoMinutos,
        Double pesoTotal,
        UnidadMedidaEnum unidadMedidaEnum,
        List<ArticuloManufacturadoDetalleResponseDto> detalle
) {}
