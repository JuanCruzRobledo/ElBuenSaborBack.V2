package org.mija.elbuensaborback.domain.model;

import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

import java.math.BigDecimal;
import java.util.List;



public abstract class Articulo {
    private Long id;
    private String denominacion;
    private BigDecimal precioVenta;
    private List<Imagen> listaImagenes;
    private UnidadMedidaEnum unidadMedidaEnum;
}
