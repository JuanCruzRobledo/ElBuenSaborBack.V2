package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloManufacturadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticuloManufacturadoJpaRepository extends JpaRepository<ArticuloManufacturadoEntity, Long> {
}
