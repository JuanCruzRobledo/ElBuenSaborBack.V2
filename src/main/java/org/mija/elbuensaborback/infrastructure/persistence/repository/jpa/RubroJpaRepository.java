package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.Rubro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RubroJpaRepository extends JpaRepository<Rubro, Long> {
}
