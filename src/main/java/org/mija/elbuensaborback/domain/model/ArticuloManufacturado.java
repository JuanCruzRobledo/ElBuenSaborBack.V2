package org.mija.elbuensaborback.domain.model;

import java.util.List;

public class ArticuloManufacturado {
    private Long id;
    private String descripcion;
    private Integer tiempoEstimadoMinutos;
    private String preparacion;
    //private double precioCosto; //Por q saco el precio del costo
    private List<ArticuloManufacturadoDetalle> articuloManufacturadoDetalle;

    public int stockCalculado(){
        return 1;
    }
//    public double precioCostoCalculado(){
//        return precioCosto;
//    }
}
