package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloInsumo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticuloInsumoJpaRepository extends JpaRepository<ArticuloInsumo, Long> {
}
