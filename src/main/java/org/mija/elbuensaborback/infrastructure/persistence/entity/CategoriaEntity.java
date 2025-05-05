package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
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
    @ManyToOne
    @JoinColumn(name = "padre_id")
    private CategoriaEntity categoriaPadre;

    // Hijos de esta categoría
    @OneToMany(mappedBy = "categoriaPadre", cascade = CascadeType.ALL)
    private Set<CategoriaEntity> subcategorias;

    @OneToMany(mappedBy = "categoria")
    private Set<ArticuloEntity> articulo;
}
