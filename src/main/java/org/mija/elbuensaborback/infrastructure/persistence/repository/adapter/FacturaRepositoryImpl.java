package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.FacturaRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.FacturaEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.FacturaJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FacturaRepositoryImpl implements FacturaRepositoryPort {
    private final FacturaJpaRepository facturaJpaRepository;

    public FacturaRepositoryImpl(FacturaJpaRepository facturaJpaRepository) {
        this.facturaJpaRepository = facturaJpaRepository;
    }

    @Override
    public Optional<FacturaEntity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<FacturaEntity> findAll() {
        return List.of();
    }

    @Override
    public FacturaEntity save(FacturaEntity entity) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<FacturaEntity> saveAll(List<FacturaEntity> entities) {
        return List.of();
    }
}
