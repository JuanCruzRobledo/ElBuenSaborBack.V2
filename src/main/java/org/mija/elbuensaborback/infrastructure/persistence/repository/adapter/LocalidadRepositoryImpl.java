package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.LocalidadRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.LocalidadEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.LocalidadJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LocalidadRepositoryImpl implements LocalidadRepositoryPort {
    private final LocalidadJpaRepository localidadJpaRepository;

    public LocalidadRepositoryImpl(LocalidadJpaRepository localidadJpaRepository) {
        this.localidadJpaRepository = localidadJpaRepository;
    }

    @Override
    public Optional<LocalidadEntity> findById(Long id) {
        return localidadJpaRepository.findById(id);
    }

    public LocalidadEntity findByNombre(String nombre) {
        return localidadJpaRepository.findByNombre(nombre);
    }

    @Override
    public List<LocalidadEntity> findAll() {
        return localidadJpaRepository.findAll();
    }

    public List<LocalidadEntity> findAllByProvinciaId(Long provinciaId) {
        return localidadJpaRepository.findAllByProvinciaId(provinciaId);
    }

    @Override
    public LocalidadEntity save(LocalidadEntity nombreEntidad) {
        return localidadJpaRepository.save(nombreEntidad);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<LocalidadEntity> saveAll(List<LocalidadEntity> localidades) {
        return localidadJpaRepository.saveAll(localidades);
    }
}
