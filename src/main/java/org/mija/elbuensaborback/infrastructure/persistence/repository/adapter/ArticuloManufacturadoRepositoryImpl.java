package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.model.ArticuloManufacturado;
import org.mija.elbuensaborback.domain.repository.ArticuloManufacturadoRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.mapper.ArticuloManufacturadoMapper;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ArticuloManufacturadoJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticuloManufacturadoRepositoryImpl implements ArticuloManufacturadoRepositoryPort {

    private final ArticuloManufacturadoJpaRepository articuloManufacturadoJpaRepository;
    private final ArticuloManufacturadoMapper articuloManufacturadoMapper;
    //Usar MAPPER PARA RECIBIR UN OBJETO DOMAIN PASARLO A ENTIDAD Y LUEGO DEVOLVER OTRO DOMAIN O DTO

    public ArticuloManufacturadoRepositoryImpl(ArticuloManufacturadoJpaRepository articuloManufacturadoJpaRepository, ArticuloManufacturadoMapper articuloManufacturadoMapper) {
        this.articuloManufacturadoJpaRepository = articuloManufacturadoJpaRepository;
        this.articuloManufacturadoMapper = articuloManufacturadoMapper;
    }


    @Override
    public Optional<ArticuloManufacturado> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<ArticuloManufacturado> findAll() {
        return List.of();
    }

    @Override
    public ArticuloManufacturado save(ArticuloManufacturado nombreEntidad) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<ArticuloManufacturado> saveAll(List<ArticuloManufacturado> articuloManufacturados) {
        return List.of();
    }
}
