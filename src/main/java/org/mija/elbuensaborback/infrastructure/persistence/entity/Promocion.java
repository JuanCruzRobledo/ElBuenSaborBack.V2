package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mija.elbuensaborback.domain.enums.TipoPromocionEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Entity
public class Promocion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String denominacion;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private LocalTime horaDesde;
    private LocalTime horaHasta;
    private String descripcionDescuento;
    private BigDecimal precioPromocional;
    @Enumerated(EnumType.STRING)
    private TipoPromocionEnum tipoPromocionEnum;

    //private articulo articulo; promocion de un articulo (manufacturado o insumo)??¿¿
    @OneToMany(mappedBy = "promocion")
    private List<Imagen> listaImagenes;

    @OneToMany(mappedBy = "promocion")
    private List<PromocionDetalle> promocionDetalle;
}
