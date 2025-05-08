package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.ArticuloManufacturadoDetalleRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloManufacturadoDetalleEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ArticuloManufacturadoDetalleJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class ArticuloManufacturadoDetalleRepositoryImpl implements ArticuloManufacturadoDetalleRepositoryPort {
    private final ArticuloManufacturadoDetalleJpaRepository articuloManufacturadoDetalleJpaRepository;

    public ArticuloManufacturadoDetalleRepositoryImpl(ArticuloManufacturadoDetalleJpaRepository articuloManufacturadoDetalleJpaRepository) {
        this.articuloManufacturadoDetalleJpaRepository = articuloManufacturadoDetalleJpaRepository;
    }

    @Override
    public Optional<ArticuloManufacturadoDetalleEntity> findById(Long aLong) {
        return articuloManufacturadoDetalleJpaRepository.findById(aLong);
    }

    @Override
    public List<ArticuloManufacturadoDetalleEntity> findAll() {
        return List.of();
    }

    @Override
    public ArticuloManufacturadoDetalleEntity save(ArticuloManufacturadoDetalleEntity entity) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<ArticuloManufacturadoDetalleEntity> saveAll(List<ArticuloManufacturadoDetalleEntity> entities) {
        return List.of();
    }
}
