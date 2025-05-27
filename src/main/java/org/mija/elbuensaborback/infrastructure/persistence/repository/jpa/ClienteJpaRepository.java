package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, Long> {
    ClienteEntity findByUsuarioEmail(String email);
}
