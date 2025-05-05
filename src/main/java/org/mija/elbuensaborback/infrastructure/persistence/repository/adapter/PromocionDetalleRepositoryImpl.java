package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.PromocionDetalleRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.PromocionDetalleEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.PromocionDetalleJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PromocionDetalleRepositoryImpl implements PromocionDetalleRepositoryPort {
    private final PromocionDetalleJpaRepository promocionDetalleJpaRepository;

    public PromocionDetalleRepositoryImpl(PromocionDetalleJpaRepository promocionDetalleJpaRepository) {
        this.promocionDetalleJpaRepository = promocionDetalleJpaRepository;
    }

    @Override
    public Optional<PromocionDetalleEntity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<PromocionDetalleEntity> findAll() {
        return List.of();
    }

    @Override
    public PromocionDetalleEntity save(PromocionDetalleEntity entity) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<PromocionDetalleEntity> saveAll(List<PromocionDetalleEntity> entities) {
        return List.of();
    }
}
