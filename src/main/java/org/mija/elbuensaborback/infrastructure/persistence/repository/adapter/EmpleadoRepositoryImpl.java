package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.EmpleadoRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.EmpleadoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.EmpleadoJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmpleadoRepositoryImpl implements EmpleadoRepositoryPort {
    private final EmpleadoJpaRepository empleadoJpaRepository;

    public EmpleadoRepositoryImpl(EmpleadoJpaRepository empleadoJpaRepository) {
        this.empleadoJpaRepository = empleadoJpaRepository;
    }

    @Override
    public Optional<EmpleadoEntity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<EmpleadoEntity> findAll() {
        return List.of();
    }

    @Override
    public EmpleadoEntity save(EmpleadoEntity entity) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<EmpleadoEntity> saveAll(List<EmpleadoEntity> entities) {
        return List.of();
    }
}
