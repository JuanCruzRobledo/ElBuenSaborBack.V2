package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Entity
public class DetallePedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cantidad;
    private BigDecimal subTotal;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private ArticuloEntity articuloEntity;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private PedidoEntity pedidoEntity;
}
