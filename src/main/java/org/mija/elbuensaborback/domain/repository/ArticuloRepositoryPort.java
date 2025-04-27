package org.mija.elbuensaborback.domain.repository;



import org.mija.elbuensaborback.infrastructure.persistence.entity.Articulo;

import java.util.List;
import java.util.Optional;

public interface ArticuloRepositoryPort{
    Optional<Articulo> findById(Long id);
    List<Articulo> findAll();
    Articulo save(Articulo nombreEntidad);
    void deleteById(Long id);
}
