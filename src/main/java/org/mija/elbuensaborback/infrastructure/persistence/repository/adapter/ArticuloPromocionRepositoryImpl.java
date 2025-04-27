package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.ArticuloPromocionRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ArticuloPromocionJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ArticuloPromocionRepositoryImpl implements ArticuloPromocionRepositoryPort {
    private final ArticuloPromocionJpaRepository articuloPromocionJpaRepository;

    public ArticuloPromocionRepositoryImpl(ArticuloPromocionJpaRepository articuloPromocionJpaRepository) {
        this.articuloPromocionJpaRepository = articuloPromocionJpaRepository;
    }
}
