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
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
public abstract class ArticuloEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String denominacion;
    private BigDecimal precioVenta;


    private Integer tiempoEstimadoMinutos;
    private BigDecimal precioCosto;


    //Estos dos tienen que estar desactivados para la baja logica
    //Si se puede usar el producto o deshabilitar su uso para compra , preparacion de otros ,etc
    private Boolean productoActivo;
    private Boolean esVendible; //True si va a aparecer en el menu?


    @OneToMany(mappedBy = "articulo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ImagenArticuloEntity> imagenesUrls;


    @ManyToOne()
    @JoinColumn(name = "categoria_id")
    private CategoriaEntity categoria;

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private SucursalEntity sucursal;

    //public abstract void descontarStock(int cantidad);

}
