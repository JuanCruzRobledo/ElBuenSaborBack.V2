package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.FacturaMercadoPagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaMercadoPagoJpaRepository extends JpaRepository<FacturaMercadoPagoEntity, Long> {
}
