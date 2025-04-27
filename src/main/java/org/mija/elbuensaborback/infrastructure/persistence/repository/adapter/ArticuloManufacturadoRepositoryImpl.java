package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.ArticuloManufacturadoRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ArticuloManufacturadoJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ArticuloManufacturadoRepositoryImpl implements ArticuloManufacturadoRepositoryPort {
    private final ArticuloManufacturadoJpaRepository articuloManufacturadoJpaRepository;

    public ArticuloManufacturadoRepositoryImpl(ArticuloManufacturadoJpaRepository articuloManufacturadoJpaRepository) {
        this.articuloManufacturadoJpaRepository = articuloManufacturadoJpaRepository;
    }
}
