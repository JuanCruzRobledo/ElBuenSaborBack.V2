package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Entity
public class PromocionDetalleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "promocion_id")
    private ArticuloPromocionEntity articuloPromocionEntity;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    // ArticuloManufacturado o ArticuloInsumo que se pueda vender
    private ArticuloEntity articuloEntity;
}
