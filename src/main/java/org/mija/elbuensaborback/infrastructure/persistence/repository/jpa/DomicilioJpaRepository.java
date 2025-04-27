package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.Domicilio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomicilioJpaRepository extends JpaRepository<Domicilio, Long> {
}
