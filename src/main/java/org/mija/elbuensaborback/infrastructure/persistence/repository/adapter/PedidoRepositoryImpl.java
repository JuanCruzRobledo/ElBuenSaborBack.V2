package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.PedidoRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.PedidoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.PedidoJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PedidoRepositoryImpl implements PedidoRepositoryPort {
    private final PedidoJpaRepository pedidoJpaRepository;

    public PedidoRepositoryImpl(PedidoJpaRepository pedidoJpaRepository) {
        this.pedidoJpaRepository = pedidoJpaRepository;
    }

    @Override
    public Optional<PedidoEntity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<PedidoEntity> findAll() {
        return List.of();
    }

    @Override
    public PedidoEntity save(PedidoEntity entity) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<PedidoEntity> saveAll(List<PedidoEntity> entities) {
        return List.of();
    }
}
