package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.DetallePedidoRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.DetallePedidoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.DetallePedidoJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DetallePedidoRepositoryImpl implements DetallePedidoRepositoryPort {
    private final DetallePedidoJpaRepository detallePedidoJpaRepository;

    public DetallePedidoRepositoryImpl(DetallePedidoJpaRepository detallePedidoJpaRepository) {
        this.detallePedidoJpaRepository = detallePedidoJpaRepository;
    }

    @Override
    public Optional<DetallePedidoEntity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<DetallePedidoEntity> findAll() {
        return List.of();
    }

    @Override
    public DetallePedidoEntity save(DetallePedidoEntity entity) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<DetallePedidoEntity> saveAll(List<DetallePedidoEntity> entities) {
        return List.of();
    }
}
