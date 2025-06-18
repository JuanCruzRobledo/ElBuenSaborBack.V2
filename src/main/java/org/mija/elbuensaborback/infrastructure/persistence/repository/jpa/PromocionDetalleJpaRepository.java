package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloPromocionEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.PromocionDetalleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface PromocionDetalleJpaRepository extends JpaRepository<PromocionDetalleEntity, Long> {

    List<PromocionDetalleEntity> findByArticuloIn(Set<ArticuloEntity> articulos);
    List<PromocionDetalleEntity>  findByArticulo(ArticuloEntity articulo);
}
