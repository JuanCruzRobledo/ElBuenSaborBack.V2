package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity(name = "articulo_manufacturado_detalle")
public class ArticuloManufacturadoDetalleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //CANTIDAD DE ESA UNIDAD DE MEDIDA
    private Double cantidad;

    @ManyToOne
    @JoinColumn(name = "articulo_manufacturado_id", nullable = false)
    private ArticuloManufacturadoEntity articuloManufacturado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "articulo_insumo_id")
    private ArticuloInsumoEntity articuloInsumo;
}
