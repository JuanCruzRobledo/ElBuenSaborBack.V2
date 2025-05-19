package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findByEmail(String email);
    Optional<UsuarioEntity> findByUsername(String email);
}
