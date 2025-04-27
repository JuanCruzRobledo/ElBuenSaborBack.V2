package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.SucursalRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.SucursalJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SucursalRepositoryImpl implements SucursalRepositoryPort {
    private final SucursalJpaRepository sucursalJpaRepository;

    public SucursalRepositoryImpl(SucursalJpaRepository sucursalJpaRepository) {
        this.sucursalJpaRepository = sucursalJpaRepository;
    }
}
