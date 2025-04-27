package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.PromocionDetalleRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.PromocionDetalleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PromocionDetalleRepositoryImpl implements PromocionDetalleRepositoryPort {
    private final PromocionDetalleJpaRepository promocionDetalleJpaRepository;

    public PromocionDetalleRepositoryImpl(PromocionDetalleJpaRepository promocionDetalleJpaRepository) {
        this.promocionDetalleJpaRepository = promocionDetalleJpaRepository;
    }
}
