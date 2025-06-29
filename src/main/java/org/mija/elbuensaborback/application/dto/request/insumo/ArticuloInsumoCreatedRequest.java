package org.mija.elbuensaborback.application.dto.request.insumo;

import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

import java.math.BigDecimal;
import java.util.Set;

public record ArticuloInsumoCreatedRequest(
        String denominacion,
        BigDecimal precioVenta,
        Boolean productoActivo,
        Integer tiempoEstimadoMinutos,
        Set<String> imagenesUrls,
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
