package org.mija.elbuensaborback.application.dto.response;

import org.mija.elbuensaborback.application.dto.global.manufacturado.ImagenDto;
import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;
import java.math.BigDecimal;
import java.util.Set;

public record ArticuloInsumoResponse(
        Long id,
        String denominacion,
        BigDecimal precioVenta,
        Boolean productoActivo,
        Integer tiempoEstimadoMinutos,
        Set<ImagenDto> imagenesUrls,
        Long categoriaId,
        String categoriaDenominacion,
        BigDecimal precioCompra,
        UnidadMedidaEnum unidadMedidaEnum,
        Double stockActual,
        Double stockMaximo,
        Double stockMinimo,
        Boolean esParaPreparar,
        Boolean esVendible
) {
}
