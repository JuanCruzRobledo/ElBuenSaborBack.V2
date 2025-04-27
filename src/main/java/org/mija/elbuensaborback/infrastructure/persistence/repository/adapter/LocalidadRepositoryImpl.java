package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.LocalidadRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.Localidad;
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
    public Optional<Localidad> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Localidad> findAll() {
        return List.of();
    }

    @Override
    public Localidad save(Localidad nombreEntidad) {
        return localidadJpaRepository.save(nombreEntidad);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Localidad> saveAll(List<Localidad> localidades) {
        return localidadJpaRepository.saveAll(localidades);
    }
}
