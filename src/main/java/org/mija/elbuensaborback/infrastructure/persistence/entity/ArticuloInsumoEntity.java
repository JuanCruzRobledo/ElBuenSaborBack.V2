package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity(name = "articulo_insumo")
@DiscriminatorValue("ARTICULO_INSUMO")
public class ArticuloInsumoEntity extends ArticuloEntity {

    @Enumerated(EnumType.STRING)
    private UnidadMedidaEnum unidadMedidaEnum;
    private Double stockActual;
    private Double stockMaximo;
    private Double stockMinimo;
    private Boolean esParaPreparar;
    //Este insumo se puede vender?
    private Boolean esVendible; //True en ensaladas preparadas o jugos preparados o asi

    //@Override
    public void descontarStock(Double cantidad) {
        double nuevoStock = this.getStockActual() - cantidad;
        if (nuevoStock < 0) {
            throw new RuntimeException("Stock insuficiente para el insumo: " + getDenominacion());
        }
        this.setStockActual(nuevoStock);
    }

}
