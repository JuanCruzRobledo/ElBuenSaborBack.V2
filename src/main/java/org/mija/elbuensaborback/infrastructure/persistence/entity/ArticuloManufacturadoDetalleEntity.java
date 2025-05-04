package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Entity
public class ArticuloManufacturadoDetalleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //CANTIDAD DE ESA UNIDAD DE MEDIDA
    private Double cantidad;

    @Enumerated(EnumType.STRING)
    private UnidadMedidaEnum unidadMedidaEnum;


    @ManyToOne
    @JoinColumn(name = "articulo_manufacturado_id")
    private ArticuloManufacturadoEntity articuloManufacturadoEntity;

    @ManyToOne
    @JoinColumn(name = "articulo_insumo_id")
    private ArticuloInsumoEntity articuloInsumoEntity;
}
