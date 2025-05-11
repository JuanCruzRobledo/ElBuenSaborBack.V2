package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.application.dto.response.ArticuloInsumoBasicResponse;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloInsumoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticuloInsumoJpaRepository extends JpaRepository<ArticuloInsumoEntity, Long> {

    @Query("SELECT new org.mija.elbuensaborback.application.dto.response.ArticuloInsumoBasicResponse(a.id, a.denominacion)  FROM articulo_insumo a")
    List<ArticuloInsumoBasicResponse> basicFindAll();
}
