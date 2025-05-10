package org.mija.elbuensaborback.application.dto.response;

import lombok.Builder;
import org.mija.elbuensaborback.application.dto.global.ArticuloManufacturadoDetalleDto;
import org.mija.elbuensaborback.application.dto.global.ImagenDto;
import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Builder
public record ArticuloManufacturadoResponse(
        Long id,
        String denominacion,
        BigDecimal precioVenta,
        Boolean productoActivo,
        Set<ImagenDto> imagenesUrls,
        Long categoriaId,
        Long sucursalId,
        String descripcion,
        BigDecimal precioCosto,
        Integer tiempoEstimadoMinutos,
        Double pesoTotal,
        UnidadMedidaEnum unidadMedidaEnum,
        List<ArticuloManufacturadoDetalleDto> articuloManufacturadoDetalle
) {}