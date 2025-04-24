package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Entity
public class Rubro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String denominacion;

    // Relación con el rubro padre
    @ManyToOne
    @JoinColumn(name = "rubro_padre_id")
    private Rubro rubroPadre;

    // Lista de subrubros
    @OneToMany(mappedBy = "rubroPadre", cascade = CascadeType.ALL)
    private Set<Rubro> subrubros;

    @OneToMany(mappedBy = "rubro")
    Set<ArticuloInsumo> articulosInsumos;

}