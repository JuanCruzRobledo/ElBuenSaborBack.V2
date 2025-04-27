package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.PaisRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.PaisJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PaisRepositoryImpl implements PaisRepositoryPort {
    private final PaisJpaRepository paisJpaRepository;

    public PaisRepositoryImpl(PaisJpaRepository paisJpaRepository) {
        this.paisJpaRepository = paisJpaRepository;
    }
}
