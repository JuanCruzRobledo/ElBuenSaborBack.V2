package org.mija.elbuensaborback.domain.model;


import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

import java.math.BigDecimal;
import java.util.List;




public class ArticuloManufacturado extends Articulo {

    private String descripcion;
    private BigDecimal precioCosto;
    private Integer tiempoEstimadoMinutos;
    private Double pesoTotal;
    private UnidadMedidaEnum unidadMedidaEnum;
    private List<ArticuloManufacturadoDetalle> articuloManufacturadoDetalle;

    public boolean sePuedePreparar(){
        return null != articuloManufacturadoDetalle;
    }

    public BigDecimal precioCostoCalculado(){
        return null;
    }
}
