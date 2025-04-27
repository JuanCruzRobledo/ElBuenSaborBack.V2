package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.RubroRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.RubroJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class RubroRepositoryImpl implements RubroRepositoryPort {
    private final RubroJpaRepository rubroJpaRepository;

    public RubroRepositoryImpl(RubroJpaRepository rubroJpaRepository) {
        this.rubroJpaRepository = rubroJpaRepository;
    }
}
