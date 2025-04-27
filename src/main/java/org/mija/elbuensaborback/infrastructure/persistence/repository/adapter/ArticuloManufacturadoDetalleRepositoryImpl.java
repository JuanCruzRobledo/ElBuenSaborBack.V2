package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.ArticuloManufacturadoDetalleRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ArticuloManufacturadoDetalleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ArticuloManufacturadoDetalleRepositoryImpl implements ArticuloManufacturadoDetalleRepositoryPort {
    private final ArticuloManufacturadoDetalleJpaRepository articuloManufacturadoDetalleJpaRepository;

    public ArticuloManufacturadoDetalleRepositoryImpl(ArticuloManufacturadoDetalleJpaRepository articuloManufacturadoDetalleJpaRepository) {
        this.articuloManufacturadoDetalleJpaRepository = articuloManufacturadoDetalleJpaRepository;
    }
}
