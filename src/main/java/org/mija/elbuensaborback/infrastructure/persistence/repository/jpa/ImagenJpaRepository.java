package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagenJpaRepository extends JpaRepository<Imagen, Long> {
}
