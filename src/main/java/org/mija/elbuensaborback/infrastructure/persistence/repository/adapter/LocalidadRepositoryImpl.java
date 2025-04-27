package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.LocalidadRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.LocalidadJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class LocalidadRepositoryImpl implements LocalidadRepositoryPort {
    private final LocalidadJpaRepository localidadJpaRepository;

    public LocalidadRepositoryImpl(LocalidadJpaRepository localidadJpaRepository) {
        this.localidadJpaRepository = localidadJpaRepository;
    }
}
