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

    //TENDRIA QUE AGREGAR UN ATRIBUTO QUE DIGA SI ES VISIBLE
    //productoActivo SERIA PARA CUANDO SI QUIERO QUE SE VEA EN EL FRONT PERO NO HAY STOCK
    //dadoDeBaja SERIA PARA CUANDO NO SE QUIERA MOSTRAR EN EL FRONT
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String denominacion;
    private BigDecimal precioVenta;
    private Boolean productoActivo;
    private Integer tiempoEstimadoMinutos;


    @OneToMany(mappedBy = "articulo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ImagenArticuloEntity> imagenesUrls;


    @ManyToOne()
    @JoinColumn(name = "categoria_id")
    private CategoriaEntity categoria;

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private SucursalEntity sucursal;

    public abstract void descontarStock(int cantidad);

}
