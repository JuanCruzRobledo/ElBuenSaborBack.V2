package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.SucursalRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.Sucursal;
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
    public Optional<Sucursal> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Sucursal> findAll() {
        return List.of();
    }

    @Override
    public Sucursal save(Sucursal nombreEntidad) {
        return sucursalJpaRepository.save(nombreEntidad);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Sucursal> saveAll(List<Sucursal> sucursales) {
        return List.of();
    }
}
