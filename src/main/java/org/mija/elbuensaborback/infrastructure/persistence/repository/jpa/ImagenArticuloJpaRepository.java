package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.ImagenArticuloEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagenArticuloJpaRepository extends JpaRepository<ImagenArticuloEntity, Long> {
}
