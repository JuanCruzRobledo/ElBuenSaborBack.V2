package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Set;



@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "articulo")
//@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ArticuloEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String denominacion;
    private BigDecimal precioVenta;
    private Boolean productoActivo;


    @OneToMany(mappedBy = "articulo")
    private Set<ImagenArticuloEntity> listaImagenes;


    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaEntity categoria;

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private SucursalEntity sucursal;

    /*
    RELACION VIEJA
    @ManyToOne
    @JoinColumn(name = "rubro_id")
    private Rubro rubro;

    @ManyToOne
    @JoinColumn(name = "subrubro_id")
    private SubRubro subrubro;
    */
}
