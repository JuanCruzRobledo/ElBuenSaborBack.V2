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

    @Builder.Default //Asegurarnos de que el builder tambien le ponga el valor 0
    private Double stockReservado = 0.0; //Se pone en 0 para todos los nuevos insumos


    public Double getStockDisponible() {
        return stockActual - stockReservado;
    }

    public void reservarStock(double cantidad, String articuloPadreNombre) {
        double cantidadBase = convertirACantidadBase(cantidad);
        if (getStockDisponible() < cantidadBase) {
            throw new StockInsuficienteException("No contamos con stock suficiente del insumo '" + this.getDenominacion() +
                    "' requerido por el artículo '" + articuloPadreNombre + "'. Disminuye la cantidad o espera reposición.");
        }
        this.stockReservado += cantidadBase;
    }

    public void liberarStock(double cantidad) {
        double cantidadBase = convertirACantidadBase(cantidad);
        this.stockReservado = Math.max(0, this.stockReservado - cantidadBase);
    }

    public void confirmarStock(double cantidad) {
        double cantidadBase = convertirACantidadBase(cantidad);
        if (this.stockReservado < cantidadBase) {
            throw new IllegalStateException("No hay suficiente stock reservado para confirmar.");
        }
        this.stockActual -= cantidadBase;
        this.stockReservado -= cantidadBase;
    }

    public void calcularPrecioVenta(BigDecimal precioVentaManual) {
        if (this.getMargen() != null && this.getPrecioCosto() != null) {
            if (this.getMargen() == 0f) {
                // Margen explícitamente 0%
                this.setPrecioVenta(this.getPrecioCosto());
            } else {
                // Calcular el precio con el margen
                BigDecimal margen = BigDecimal.valueOf(this.getMargen()).divide(BigDecimal.valueOf(100));
                BigDecimal nuevoPrecioVenta = this.getPrecioCosto().multiply(BigDecimal.ONE.add(margen));
                this.setPrecioVenta(nuevoPrecioVenta);
            }
        } else {
            // Si no hay margen, usar el precio ingresado manualmente
            this.setPrecioVenta(precioVentaManual);
        }
    }

    public double convertirACantidadBase(double cantidad) {
        return cantidad * unidadMedidaEnum.getFactor();
    }

}
