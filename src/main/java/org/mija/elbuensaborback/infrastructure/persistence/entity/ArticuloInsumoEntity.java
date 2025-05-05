package org.mija.elbuensaborback.infrastructure.persistence.entity;

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
public class ArticuloInsumoEntity extends ArticuloEntity {

    private BigDecimal precioCompra;
    @Enumerated(EnumType.STRING)
    private UnidadMedidaEnum unidadMedidaEnum;
    private Double stockActual;
    private Double stockMaximo;
    private Double stockMinimo;
    private Boolean esParaPreparar;
    //Este insumo se puede vender?
    private Boolean esVendible; //True en ensaladas preparadas o jugos preparados o asi
}
