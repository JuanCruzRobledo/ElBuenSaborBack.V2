package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.ProvinciaRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ProvinciaJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProvinciaRepositoryImpl implements ProvinciaRepositoryPort {
    private final ProvinciaJpaRepository provinciaJpaRepository;

    public ProvinciaRepositoryImpl(ProvinciaJpaRepository provinciaJpaRepository) {
        this.provinciaJpaRepository = provinciaJpaRepository;
    }
}
