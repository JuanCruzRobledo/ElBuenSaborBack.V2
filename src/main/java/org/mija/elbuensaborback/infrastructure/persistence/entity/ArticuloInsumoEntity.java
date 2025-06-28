package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;
import org.mija.elbuensaborback.domain.exceptions.StockInsuficienteException;

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


    //@Override
    public void descontarStock(Double cantidad, String articuloPadreNombre) {
        double nuevoStock = this.getStockActual() - cantidad;
        if (nuevoStock < 0) {
            throw new StockInsuficienteException(
                    "No hay suficiente stock de insumo '" + this.getDenominacion() +
                            "' requerido por el artículo '" + articuloPadreNombre + "'"
            );
        }
        this.setStockActual(nuevoStock);
    }

}
