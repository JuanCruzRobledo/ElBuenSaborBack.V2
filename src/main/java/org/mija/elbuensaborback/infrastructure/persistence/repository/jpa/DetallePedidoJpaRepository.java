package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.DetallePedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetallePedidoJpaRepository extends JpaRepository<DetallePedidoEntity, Long> {
}
