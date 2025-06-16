package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.ProvinciaRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ProvinciaEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ProvinciaJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProvinciaRepositoryImpl implements ProvinciaRepositoryPort {
    private final ProvinciaJpaRepository provinciaJpaRepository;

    public ProvinciaRepositoryImpl(ProvinciaJpaRepository provinciaJpaRepository) {
        this.provinciaJpaRepository = provinciaJpaRepository;
    }

    @Override
    public Optional<ProvinciaEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<ProvinciaEntity> findAll() {
        return provinciaJpaRepository.findAll();
    }

    public List<ProvinciaEntity> findAllByPaisId(Long id) {
        return provinciaJpaRepository.findAllByPaisId(id);
    }

    @Override
    public ProvinciaEntity save(ProvinciaEntity nombreEntidad) {
        return provinciaJpaRepository.save(nombreEntidad);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<ProvinciaEntity> saveAll(List<ProvinciaEntity> localidades) {
        return List.of();
    }
}
