package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.SucursalRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.SucursalEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.SucursalJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SucursalRepositoryImpl implements SucursalRepositoryPort {
    private final SucursalJpaRepository sucursalJpaRepository;

    public SucursalRepositoryImpl(SucursalJpaRepository sucursalJpaRepository) {
        this.sucursalJpaRepository = sucursalJpaRepository;
    }

    @Override
    public Optional<SucursalEntity> findById(Long id) {
         return sucursalJpaRepository.findById(id);
    }

    @Override
    public List<SucursalEntity> findAll() {
        return List.of();
    }

    @Override
    public SucursalEntity save(SucursalEntity nombreEntidad) {
        return sucursalJpaRepository.save(nombreEntidad);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<SucursalEntity> saveAll(List<SucursalEntity> sucursales) {
        return List.of();
    }
}
