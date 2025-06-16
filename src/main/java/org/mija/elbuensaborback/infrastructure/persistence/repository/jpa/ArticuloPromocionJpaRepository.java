package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloPromocionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticuloPromocionJpaRepository extends JpaRepository<ArticuloPromocionEntity, Long> {
    List<ArticuloPromocionEntity> findAllByEsVendible(boolean esVendible);
}
