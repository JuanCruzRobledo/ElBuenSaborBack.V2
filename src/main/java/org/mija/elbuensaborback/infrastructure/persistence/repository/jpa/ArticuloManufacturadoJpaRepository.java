package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoBasicResponse;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloManufacturadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticuloManufacturadoJpaRepository extends JpaRepository<ArticuloManufacturadoEntity, Long> {


    //(a.id, a.denominacion, a.descripcion, a.categoria, a.precioVenta, a.imagenesUrls, a.tiempoEstimadoMinutos, a.productoActivo, a.articuloManufacturadoDetalle)
    @Query("SELECT a FROM articulo_manufacturado a WHERE a.esVendible = true")
    List<ArticuloManufacturadoEntity> findAllBasicForSell();
}
