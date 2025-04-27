package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.DetallePedidoRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.DetallePedidoJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DetallePedidoRepositoryImpl implements DetallePedidoRepositoryPort {
    private final DetallePedidoJpaRepository detallePedidoJpaRepository;

    public DetallePedidoRepositoryImpl(DetallePedidoJpaRepository detallePedidoJpaRepository) {
        this.detallePedidoJpaRepository = detallePedidoJpaRepository;
    }
}
