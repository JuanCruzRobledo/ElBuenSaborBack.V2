package org.mija.elbuensaborback.application.dto.response;

import lombok.Builder;
import org.mija.elbuensaborback.application.dto.global.manufacturado.ArticuloManufacturadoDetalleDto;
import org.mija.elbuensaborback.application.dto.global.manufacturado.ImagenDto;
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
        Integer tiempoEstimadoMinutos,
        Set<ImagenDto> imagenesUrls,
        Long categoriaId,
        String descripcion,
        BigDecimal precioCosto,
        Double pesoTotal,
        UnidadMedidaEnum unidadMedidaEnum,
        List<ArticuloManufacturadoDetalleDto> articuloManufacturadoDetalle
) {}