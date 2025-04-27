package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.DomicilioRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.DomicilioJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DomicilioRepositoryImpl implements DomicilioRepositoryPort {
    private final DomicilioJpaRepository domicilioJpaRepository;

    public DomicilioRepositoryImpl(DomicilioJpaRepository domicilioJpaRepository) {
        this.domicilioJpaRepository = domicilioJpaRepository;
    }
}
