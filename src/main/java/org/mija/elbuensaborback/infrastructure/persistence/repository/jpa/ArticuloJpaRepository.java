package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticuloJpaRepository extends JpaRepository<ArticuloEntity, Long> {
}
