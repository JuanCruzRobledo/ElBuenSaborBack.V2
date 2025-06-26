package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ArticuloJpaRepository extends JpaRepository<ArticuloEntity, Long> {

    @Query("SELECT a FROM articulo a WHERE LOWER(a.denominacion) = LOWER(:denominacion)")
    Optional<ArticuloEntity> findByDenominacion(String denominacion);
}
