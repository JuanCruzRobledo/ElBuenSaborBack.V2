package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.FacturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaJpaRepository extends JpaRepository<FacturaEntity, Long> {
}
