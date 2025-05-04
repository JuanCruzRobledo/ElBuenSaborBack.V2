package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.LocalidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalidadJpaRepository extends JpaRepository<LocalidadEntity, Long> {
}
