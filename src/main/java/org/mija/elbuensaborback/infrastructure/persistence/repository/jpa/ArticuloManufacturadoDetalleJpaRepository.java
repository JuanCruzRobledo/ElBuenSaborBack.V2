package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloManufacturadoDetalleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticuloManufacturadoDetalleJpaRepository extends JpaRepository<ArticuloManufacturadoDetalleEntity, Long> {


    @Query("SELECT d FROM org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloManufacturadoDetalleEntity d JOIN FETCH d.articuloInsumo WHERE d.articuloManufacturado.id = :idManufacturado")
    List<ArticuloManufacturadoDetalleEntity> findAllByIdArticuloManufacturado(@Param("idManufacturado") Long idManufacturado);
}
