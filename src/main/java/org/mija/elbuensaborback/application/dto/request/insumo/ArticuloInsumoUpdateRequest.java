package org.mija.elbuensaborback.application.dto.request.insumo;

import org.mija.elbuensaborback.application.dto.global.manufacturado.ImagenDto;
import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

import java.math.BigDecimal;
import java.util.Set;

public record ArticuloInsumoUpdateRequest(
        String denominacion,
        BigDecimal precioVenta,
        Boolean productoActivo,
        Integer tiempoEstimadoMinutos,
        Set<ImagenDto> imagenesUrls,
        Long categoriaId,
        BigDecimal precioCosto,
        UnidadMedidaEnum unidadMedidaEnum,
        Double stockActual,
        Double stockMaximo,
        Double stockMinimo,
        Boolean esParaPreparar,
        Boolean esVendible,
        Float margen
) {
}
