package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.model.ArticuloManufacturado;
import org.mija.elbuensaborback.domain.repository.ArticuloManufacturadoRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloManufacturadoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ArticuloManufacturadoJpaRepository;
import org.mija.elbuensaborback.infrastructure.persistence.repository.mapper.ArticuloManufacturadoEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticuloManufacturadoRepositoryImpl implements ArticuloManufacturadoRepositoryPort {

    private final ArticuloManufacturadoJpaRepository articuloManufacturadoJpaRepository;
    private final ArticuloManufacturadoEntityMapper articuloManufacturadoMapper;
    //Usar MAPPER PARA RECIBIR UN OBJETO DOMAIN PASARLO A ENTIDAD Y LUEGO DEVOLVER OTRO DOMAIN O DTO

    public ArticuloManufacturadoRepositoryImpl(ArticuloManufacturadoJpaRepository articuloManufacturadoJpaRepository, ArticuloManufacturadoEntityMapper articuloManufacturadoMapper) {
        this.articuloManufacturadoJpaRepository = articuloManufacturadoJpaRepository;
        this.articuloManufacturadoMapper = articuloManufacturadoMapper;
    }


    @Override
    public Optional<ArticuloManufacturadoEntity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<ArticuloManufacturadoEntity> findAll() {
        return List.of();
    }

    @Override
    public ArticuloManufacturadoEntity save(ArticuloManufacturadoEntity entity) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<ArticuloManufacturadoEntity> saveAll(List<ArticuloManufacturadoEntity> entities) {
        return List.of();
    }
}
