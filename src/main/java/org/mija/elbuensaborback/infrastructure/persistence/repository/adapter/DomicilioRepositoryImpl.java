package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.DomicilioRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.DomicilioEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.DomicilioJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DomicilioRepositoryImpl implements DomicilioRepositoryPort {
    private final DomicilioJpaRepository domicilioJpaRepository;

    public DomicilioRepositoryImpl(DomicilioJpaRepository domicilioJpaRepository) {
        this.domicilioJpaRepository = domicilioJpaRepository;
    }

    @Override
    public Optional<DomicilioEntity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<DomicilioEntity> findAll() {
        return List.of();
    }

    @Override
    public DomicilioEntity save(DomicilioEntity entity) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<DomicilioEntity> saveAll(List<DomicilioEntity> entities) {
        return List.of();
    }
}
