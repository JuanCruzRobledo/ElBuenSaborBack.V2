package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.Localidad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalidadJpaRepository extends JpaRepository<Localidad, Long> {
}
