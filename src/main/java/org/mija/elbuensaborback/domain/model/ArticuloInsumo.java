package org.mija.elbuensaborback.domain.model;


import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

import java.math.BigDecimal;



public class ArticuloInsumo extends Articulo {

    private BigDecimal precioCompra;
    private UnidadMedidaEnum unidadMedidaEnum;
    private Double stockActual;
    private Double stockMaximo;
    private Double stockMinimo;
    private Boolean esParaPreparar;
    //Este insumo se puede vender?
    private Boolean esVendible; //True en ensaladas preparadas o jugos preparados o asi
}
