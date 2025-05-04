package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.PaisEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaisJpaRepository extends JpaRepository<PaisEntity, Long> {
}
