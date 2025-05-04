package org.mija.elbuensaborback.domain.model;

import jakarta.persistence.*;
import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;


public class ArticuloManufacturadoDetalle {

    private Long id;
    private Double cantidad;
    private UnidadMedidaEnum unidadMedidaEnum;
    private ArticuloManufacturado articuloManufacturadoEntity;
    private ArticuloInsumo articuloInsumoEntity;
}
