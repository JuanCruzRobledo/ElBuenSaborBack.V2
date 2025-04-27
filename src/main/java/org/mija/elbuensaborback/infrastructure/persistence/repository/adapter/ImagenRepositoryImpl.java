package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.ImagenRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ImagenJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ImagenRepositoryImpl implements ImagenRepositoryPort {
    private final ImagenJpaRepository imagenJpaRepository;

    public ImagenRepositoryImpl(ImagenJpaRepository imagenJpaRepository) {
        this.imagenJpaRepository = imagenJpaRepository;
    }
}
