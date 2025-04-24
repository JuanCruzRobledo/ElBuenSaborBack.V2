package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Entity
public class ArticuloInsumo extends Articulo {

    private BigDecimal precioCompra;
    private Integer stockActual;
    private Integer stockMaximo;
    private Integer stockMinimo;
    private Boolean esParaPreparar;
}
