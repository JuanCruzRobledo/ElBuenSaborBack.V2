package org.mija.elbuensaborback.domain.repository;

import org.mija.elbuensaborback.infrastructure.persistence.entity.Sucursal;

import java.util.List;
import java.util.Optional;

public interface SucursalRepositoryPort {
    Optional<Sucursal> findById(Long id);
    List<Sucursal> findAll();
    Sucursal save(Sucursal nombreEntidad);
    void deleteById(Long id);
    List<Sucursal> saveAll(List<Sucursal> sucursales);
}
