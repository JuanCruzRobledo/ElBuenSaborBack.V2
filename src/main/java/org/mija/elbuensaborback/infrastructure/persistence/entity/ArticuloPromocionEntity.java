package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity(name = "articulo_promocion")
public class ArticuloPromocionEntity extends ArticuloEntity {

    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private LocalTime horaDesde;
    private LocalTime horaHasta;
    private String descripcionDescuento;
    private String nombre;

    @OneToMany(mappedBy = "articuloPromocion")
    private List<PromocionDetalleEntity> promocionDetalle;

    /*
    public void descontarStock(int cantidad) {
        for (PromocionDetalleEntity detalle : this.getPromocionDetalle()) {
            detalle.getArticulo().descontarStock(detalle.getCantidad() * cantidad);
        }
    }

     */


    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;
    //private String denominacion;
    //private BigDecimal precioPromocional;
    //private Articulo articulo; promocion de un articulo (manufacturado o insumo)??¿¿
    //@OneToMany(mappedBy = "promocion")
    //private List<Imagen> listaImagenes;
}
