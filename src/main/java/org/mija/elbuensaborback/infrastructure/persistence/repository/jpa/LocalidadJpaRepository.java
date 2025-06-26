package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.LocalidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocalidadJpaRepository extends JpaRepository<LocalidadEntity, Long> {
    @Query("SELECT l FROM localidad l WHERE LOWER(l.nombre) = LOWER(:nombre)")
    LocalidadEntity findByNombre(@Param("nombre") String nombre);

    List<LocalidadEntity> findAllByProvinciaId (Long provinciaId);
}
