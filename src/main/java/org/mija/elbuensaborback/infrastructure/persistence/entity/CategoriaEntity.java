package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "categoria")
public class CategoriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String denominacion;

    // Referencia al padre (puede ser null si es categoría raíz)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "padre_id")
    private CategoriaEntity categoriaPadre;

    // Hijos de esta categoría
    @OneToMany(mappedBy = "categoriaPadre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CategoriaEntity> subcategorias;

    @OneToMany(mappedBy = "categoria")
    private Set<ArticuloEntity> articulo;
}
