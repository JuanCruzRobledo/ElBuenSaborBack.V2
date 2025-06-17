package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.application.dto.response.RankingManufacturadoResponse;
import org.mija.elbuensaborback.infrastructure.persistence.entity.DetallePedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DetallePedidoJpaRepository extends JpaRepository<DetallePedidoEntity, Long> {

    // ======================== TODOS  ===========================
    @Query("""
    SELECT new org.mija.elbuensaborback.application.dto.response.RankingManufacturadoResponse(
        a.denominacion,
        SUM(d.cantidad),
        SUM(d.subTotal)
    )
    FROM pedido_detalle d
    JOIN d.pedido p
    JOIN d.articulo a
    WHERE p.estadoEnum = 'TERMINADO'
     AND TYPE(a) = articulo_manufacturado
    GROUP BY a.id, a.denominacion
    ORDER BY SUM(d.cantidad) DESC
""")
    List<RankingManufacturadoResponse> rankingArticulosMasPedidos();


    // ======================== ENTRE FECHA INICIO Y FIN ===========================

    @Query("""
    SELECT new org.mija.elbuensaborback.application.dto.response.RankingManufacturadoResponse(
        a.denominacion,
        SUM(d.cantidad),
        SUM(d.subTotal)
    )
    FROM pedido_detalle d
    JOIN d.pedido p
    JOIN d.articulo a
    WHERE p.estadoEnum = 'TERMINADO'
    AND TYPE(a) = articulo_manufacturado
    AND p.fechaPedido BETWEEN :fechaInicio AND :fechaFin
    GROUP BY a.id, a.denominacion
    ORDER BY SUM(d.cantidad) DESC
""")
    List<RankingManufacturadoResponse> rankingArticulosMasPedidosEntreFechas(
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin
    );


    // ======================== DESDE FECHA INICIO ===========================
    @Query("""
    SELECT new org.mija.elbuensaborback.application.dto.response.RankingManufacturadoResponse(
        a.denominacion,
        SUM(d.cantidad),
        SUM(d.subTotal)
    )
    FROM pedido_detalle d
    JOIN d.pedido p
    JOIN d.articulo a
    WHERE p.estadoEnum = 'TERMINADO'
    AND TYPE(a) = articulo_manufacturado
    AND p.fechaPedido >= :fechaInicio
    GROUP BY a.id, a.denominacion
    ORDER BY SUM(d.cantidad) DESC
""")
    List<RankingManufacturadoResponse> rankingArticulosMasPedidosDesdeFecha(@Param("fechaInicio") LocalDate fechaInicio);

    // ======================== DESDE FECHA INICIO ===========================
    @Query("""
    SELECT new org.mija.elbuensaborback.application.dto.response.RankingManufacturadoResponse(
        a.denominacion,
        SUM(d.cantidad),
        SUM(d.subTotal)
    )
    FROM pedido_detalle d
    JOIN d.pedido p
    JOIN d.articulo a
    WHERE p.estadoEnum = 'TERMINADO'
    AND TYPE(a) = articulo_manufacturado
    AND p.fechaPedido <= :fechaFin
    GROUP BY a.id, a.denominacion
    ORDER BY SUM(d.cantidad) DESC
""")
    List<RankingManufacturadoResponse> rankingArticulosMasPedidosHastaFecha(@Param("fechaFin") LocalDate fechaFin);



}
