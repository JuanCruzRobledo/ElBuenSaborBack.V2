package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloInsumoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticuloInsumoJpaRepository extends JpaRepository<ArticuloInsumoEntity, Long> {
}
