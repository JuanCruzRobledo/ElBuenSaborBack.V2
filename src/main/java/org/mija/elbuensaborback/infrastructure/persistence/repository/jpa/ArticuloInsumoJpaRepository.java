package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.application.dto.response.ArticuloInsumoBasicResponse;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloInsumoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticuloInsumoJpaRepository extends JpaRepository<ArticuloInsumoEntity, Long> {

    @Query("SELECT new org.mija.elbuensaborback.application.dto.response.ArticuloInsumoBasicResponse(a.id, a.denominacion)  FROM articulo_insumo a")
    List<ArticuloInsumoBasicResponse> basicFindAll();

    @Query("SELECT new org.mija.elbuensaborback.application.dto.response.ArticuloInsumoBasicResponse(a.id, a.denominacion)  FROM articulo_insumo a WHERE a.esParaPreparar = true")
    List<ArticuloInsumoBasicResponse> basicFindAllParaPreparar();

    ArticuloInsumoEntity findByDenominacion(String denominacion);

    @Query("SELECT a FROM articulo_insumo a WHERE a.categoria.denominacion = :denominacion AND a.esVendible = true")
    List<ArticuloInsumoEntity> findAllByCategoriaDenominacionVendibles(@Param("denominacion") String denominacion);

    @Query("SELECT a FROM articulo_insumo a JOIN categoria c WHERE a.esVendible = true")
    List<ArticuloInsumoEntity> findAllAndEsVendibleTrueAndCategoria();

}
