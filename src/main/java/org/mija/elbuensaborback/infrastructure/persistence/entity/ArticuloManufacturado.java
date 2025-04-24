package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Entity
public class ArticuloManufacturado extends Articulo {

    private String descripcion;
    private BigDecimal precioCosto;
    private Integer tiempoEstimadoMinutos;
    //private String preparacion; no se si iria

    @OneToMany(mappedBy = "articuloManufacturado")
    private List<ArticuloManufacturadoDetalle> articuloManufacturadoDetalle;

    public int stockCalculado(){
        return 1;
    }
//    public double precioCostoCalculado(){
//        return precioCosto;
//    }
}
