package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.EmpleadoRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.EmpleadoJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class EmpleadoRepositoryImpl implements EmpleadoRepositoryPort {
    private final EmpleadoJpaRepository empleadoJpaRepository;

    public EmpleadoRepositoryImpl(EmpleadoJpaRepository empleadoJpaRepository) {
        this.empleadoJpaRepository = empleadoJpaRepository;
    }
}
