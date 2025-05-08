package org.mija.elbuensaborback.application.dto.request;

import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

import java.math.BigDecimal;

public interface ArticuloManufacturadoBaseRequest {
    String denominacion();
    BigDecimal precioVenta();
    String descripcion();
    BigDecimal precioCosto();
    int tiempoEstimadoMinutos();
    UnidadMedidaEnum unidadMedidaEnum();
    Long categoriaId();
    Long sucursalId();
}
