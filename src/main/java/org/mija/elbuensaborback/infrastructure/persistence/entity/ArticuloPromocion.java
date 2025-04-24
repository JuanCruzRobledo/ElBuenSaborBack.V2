package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.mija.elbuensaborback.domain.enums.TipoPromocionEnum;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Entity
public class ArticuloPromocion extends Articulo {

    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private LocalTime horaDesde;
    private LocalTime horaHasta;
    private String descripcionDescuento;

    @Enumerated(EnumType.STRING)
    private TipoPromocionEnum tipoPromocionEnum;

    @OneToMany(mappedBy = "articuloPromocion")
    private List<PromocionDetalle> promocionDetalle;



    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;
    //private String denominacion;
    //private BigDecimal precioPromocional;
    //private Articulo articulo; promocion de un articulo (manufacturado o insumo)??¿¿
    //@OneToMany(mappedBy = "promocion")
    //private List<Imagen> listaImagenes;
}
