package org.mija.elbuensaborback.domain.repository;



import org.mija.elbuensaborback.domain.model.ArticuloManufacturado;

import java.util.List;
import java.util.Optional;

public interface ArticuloManufacturadoRepositoryPort {
    Optional<ArticuloManufacturado> findById(Long id);
    List<ArticuloManufacturado> findAll();
    ArticuloManufacturado save(ArticuloManufacturado nombreEntidad);
    void deleteById(Long id);
    List<ArticuloManufacturado> saveAll(List<ArticuloManufacturado> articuloManufacturados);
}
