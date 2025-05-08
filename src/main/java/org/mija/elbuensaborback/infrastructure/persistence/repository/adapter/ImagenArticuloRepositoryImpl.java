package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.ImagenArticuloRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ImagenArticuloEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ImagenArticuloJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ImagenArticuloRepositoryImpl implements ImagenArticuloRepositoryPort {
    private final ImagenArticuloJpaRepository imagenArticuloJpaRepository;

    public ImagenArticuloRepositoryImpl(ImagenArticuloJpaRepository imagenArticuloJpaRepository) {
        this.imagenArticuloJpaRepository = imagenArticuloJpaRepository;
    }

    @Override
    public Optional<ImagenArticuloEntity> findById(Long aLong) {
        return imagenArticuloJpaRepository.findById(aLong);
    }

    @Override
    public List<ImagenArticuloEntity> findAll() {
        return List.of();
    }

    @Override
    public ImagenArticuloEntity save(ImagenArticuloEntity entity) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<ImagenArticuloEntity> saveAll(List<ImagenArticuloEntity> entities) {
        return List.of();
    }
}
