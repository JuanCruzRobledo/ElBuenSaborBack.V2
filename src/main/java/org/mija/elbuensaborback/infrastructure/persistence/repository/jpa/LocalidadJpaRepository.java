package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.LocalidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocalidadJpaRepository extends JpaRepository<LocalidadEntity, Long> {
    LocalidadEntity findByNombre (String nombre);
    List<LocalidadEntity> findAllByProvinciaId (Long provinciaId);
}
