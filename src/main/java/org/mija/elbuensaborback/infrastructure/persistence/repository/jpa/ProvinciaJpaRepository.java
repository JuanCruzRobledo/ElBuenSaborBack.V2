package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinciaJpaRepository extends JpaRepository<Provincia, Long> {
}
