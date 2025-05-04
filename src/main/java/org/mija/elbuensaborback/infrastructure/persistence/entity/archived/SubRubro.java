package org.mija.elbuensaborback.infrastructure.persistence.entity.archived;

import jakarta.persistence.*;
import lombok.*;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloEntity;

import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
//@Entity
@Deprecated
public class SubRubro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String denominacion;

    @ManyToOne
    @JoinColumn(name = "rubro_padre_id")
    private Rubro rubroPadre;

    //Relacion BiDireccional

    @OneToMany(mappedBy = "subrubroPadre", cascade = CascadeType.ALL)
    private Set<SubRubro> subrubros;

    @ManyToOne
    @JoinColumn(name = "subrubro_padre_id")
    private SubRubro subrubroPadre;

    //Fin

    @OneToMany(mappedBy = "subrubro")
    Set<ArticuloEntity> articuloEntities;

}
