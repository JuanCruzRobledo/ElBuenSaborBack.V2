package org.mija.elbuensaborback.infrastructure.persistence.entity.archived;

import jakarta.persistence.*;
import lombok.*;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloEntity;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
//@Entity
@Deprecated
public class Rubro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String denominacion;

    // Lista de subrubros
    @OneToMany(mappedBy = "rubroPadre", cascade = CascadeType.ALL)
    private Set<SubRubro> subrubros;

    @OneToMany(mappedBy = "rubro")
    Set<ArticuloEntity> articuloEntities;

}