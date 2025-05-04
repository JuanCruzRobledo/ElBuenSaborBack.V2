package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.PromocionDetalleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromocionDetalleJpaRepository extends JpaRepository<PromocionDetalleEntity, Long> {
}
