package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoBasicResponse;
import org.mija.elbuensaborback.domain.repository.ArticuloManufacturadoRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloManufacturadoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ArticuloManufacturadoJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticuloManufacturadoRepositoryImpl implements ArticuloManufacturadoRepositoryPort {

    private final ArticuloManufacturadoJpaRepository articuloManufacturadoJpaRepository;


    public ArticuloManufacturadoRepositoryImpl(ArticuloManufacturadoJpaRepository articuloManufacturadoJpaRepository) {
        this.articuloManufacturadoJpaRepository = articuloManufacturadoJpaRepository;
    }

    //Usar MAPPER PARA RECIBIR UN OBJETO DOMAIN PASARLO A ENTIDAD Y LUEGO DEVOLVER OTRO DOMAIN O DTO


    @Override
    public Optional<ArticuloManufacturadoEntity> findById(Long aLong) {
        return articuloManufacturadoJpaRepository.findById(aLong);
    }

    @Override
    public List<ArticuloManufacturadoEntity> findAll() {
        return articuloManufacturadoJpaRepository.findAll();
    }

    //CAMBIAR POR ArticuloManufacturadoBasicResponse
    public List<ArticuloManufacturadoEntity> findAllBasicForSell() {

        return articuloManufacturadoJpaRepository.findAllBasicForSell();
    }

    @Override
    public ArticuloManufacturadoEntity save(ArticuloManufacturadoEntity entity) {
        return articuloManufacturadoJpaRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        articuloManufacturadoJpaRepository.deleteById(aLong);
    }

    @Override
    public List<ArticuloManufacturadoEntity> saveAll(List<ArticuloManufacturadoEntity> entities) {
        return List.of();
    }
}
