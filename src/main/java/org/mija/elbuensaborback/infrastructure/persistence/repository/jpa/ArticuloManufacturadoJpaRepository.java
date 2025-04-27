package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloManufacturado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticuloManufacturadoJpaRepository extends JpaRepository<ArticuloManufacturado, Long> {
}
