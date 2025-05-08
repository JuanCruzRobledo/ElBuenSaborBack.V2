package org.mija.elbuensaborback.application.dto.request;

import lombok.Builder;
import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Builder
public record ArticuloManufacturadoUpdateRequest(
        String denominacion,
        BigDecimal precioVenta,
        String descripcion,
        BigDecimal precioCosto,
        int tiempoEstimadoMinutos,
        UnidadMedidaEnum unidadMedidaEnum,
        Long categoriaId,
        Long sucursalId,
        Set<ImagenRequest> imagenesUrls,
        List<ArticuloManufacturadoDetalleUpdateRequest> articuloManufacturadoDetalle
) implements ArticuloManufacturadoBaseRequest {}