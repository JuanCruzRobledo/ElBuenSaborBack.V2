package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;

import java.math.BigDecimal;
import java.util.List;



@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Articulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String denominacion;
    private BigDecimal precioVenta;
    @OneToMany(mappedBy = "articulo")
    private List<Imagen> listaImagenes;
    @ManyToOne
    @JoinColumn(name = "rubro_id")
    private Rubro rubro;
    @Enumerated(EnumType.STRING)
    private UnidadMedidaEnum unidadMedidaEnum;

}
