package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoriaJpaRepository extends JpaRepository<CategoriaEntity, Long> {
    @EntityGraph(attributePaths = {"subcategorias", "categoriaPadre"})
    Optional<CategoriaEntity> findWithSubcategoriasById(Long id);

    List<CategoriaEntity> findByCategoriaPadreIsNull();

    @Query("SELECT c FROM categoria c WHERE LOWER(c.denominacion) = LOWER(:denominacion)")
    CategoriaEntity findByDenominacion(@Param("denominacion") String denominacion);

}
