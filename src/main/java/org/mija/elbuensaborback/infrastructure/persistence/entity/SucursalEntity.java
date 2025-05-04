package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SucursalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private LocalTime horarioApertura;
    private LocalTime horarioCierre;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "domicilio_id", referencedColumnName = "id")
    private DomicilioEntity domicilioEntity;

    /*
    AHORA PROMOCION ES UN ARTICULO
    @ManyToMany
    @JoinTable(
            name = "sucursal_promociones",
            joinColumns = @JoinColumn(name = "sucursal_id") ,
            inverseJoinColumns = @JoinColumn(name = "promocion_id")
    )
    private List<Promocion> listaPromociones;

    */

    /*
    AHORA SE RELACIONA DIRECTAMENTE CON ARTICULO QUE SI TIENE UN RUBRO
    @ManyToMany
    @JoinTable(
            name = "sucursal_rubro",
            joinColumns = @JoinColumn(
                    name = "sucursal_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "rubro_id"
            )
    )
    private List<Rubro> listaRubros;
    */

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private EmpresaEntity empresaEntity;

    @ManyToMany
    @JoinTable(
            name = "sucursal_persona",
            joinColumns = @JoinColumn(
                    name = "sucursal_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "persona_id"
            )
    )
    private List<PersonaEntity> listaPersonaEntities;

    @OneToMany(mappedBy = "sucursal")
    private List<PedidoEntity> listaPedidoEntities;

    @OneToMany(mappedBy = "sucursal")
    private List<ArticuloEntity> listaArticuloEntities;

}
