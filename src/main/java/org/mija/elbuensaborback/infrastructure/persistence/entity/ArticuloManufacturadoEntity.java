package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

import java.math.BigDecimal;
import java.util.List;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class ArticuloManufacturadoEntity extends ArticuloEntity {

    private String descripcion;
    private BigDecimal precioCosto;
    private Integer tiempoEstimadoMinutos;

    //CANTIDAD TOTAL DE LA UNIDAD DE MEDIDA EN ESTE ARTICULO MANUFACTURADO
    private Double pesoTotal;
    @Enumerated(EnumType.STRING)
    private UnidadMedidaEnum unidadMedidaEnum;

    //private String preparacion; no se si iria

    @OneToMany(mappedBy = "articuloManufacturado", cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<ArticuloManufacturadoDetalleEntity> articuloManufacturadoDetalleEntity;

    public boolean sePuedePreparar(){
        return null != articuloManufacturadoDetalleEntity;
    }

    public BigDecimal precioCostoCalculado(){
        return null;
    }
}
