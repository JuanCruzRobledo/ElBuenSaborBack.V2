package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.DatosMercadoPagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaMercadoPagoJpaRepository extends JpaRepository<DatosMercadoPagoEntity, Long> {
}
