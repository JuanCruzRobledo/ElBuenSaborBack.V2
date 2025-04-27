package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.FacturaRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.FacturaJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class FacturaRepositoryImpl implements FacturaRepositoryPort {
    private final FacturaJpaRepository facturaJpaRepository;

    public FacturaRepositoryImpl(FacturaJpaRepository facturaJpaRepository) {
        this.facturaJpaRepository = facturaJpaRepository;
    }
}
