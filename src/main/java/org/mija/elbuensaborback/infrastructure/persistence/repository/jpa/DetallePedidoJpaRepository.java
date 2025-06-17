package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.application.dto.response.RankingArticuloResponse;
import org.mija.elbuensaborback.infrastructure.persistence.entity.DetallePedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DetallePedidoJpaRepository extends JpaRepository<DetallePedidoEntity, Long> {

    // ======================================
    // ==== RANKING ARTÍCULOS MANUFACTURADOS
    // ======================================

    // 🔹 Todos los artículos manufacturados más pedidos
    @Query("""
        SELECT new org.mija.elbuensaborback.application.dto.response.RankingArticuloResponse(
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
    List<RankingArticuloResponse> rankingArticulosManufacturadosMasPedidos();

    // 🔹 Entre fechas
    @Query("""
        SELECT new org.mija.elbuensaborback.application.dto.response.RankingArticuloResponse(
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
    List<RankingArticuloResponse> rankingArticulosManufacturadosMasPedidosEntreFechas(
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin
    );

    // 🔹 Desde fecha
    @Query("""
        SELECT new org.mija.elbuensaborback.application.dto.response.RankingArticuloResponse(
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
    List<RankingArticuloResponse> rankingArticulosManufacturadosMasPedidosDesdeFecha(
            @Param("fechaInicio") LocalDate fechaInicio
    );

    // 🔹 Hasta fecha
    @Query("""
        SELECT new org.mija.elbuensaborback.application.dto.response.RankingArticuloResponse(
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
    List<RankingArticuloResponse> rankingArticulosManufacturadosMasPedidosHastaFecha(
            @Param("fechaFin") LocalDate fechaFin
    );

    // ======================================
    // ===== RANKING ARTÍCULOS DE INSUMOS ===
    // ======================================

    // 🔹 Todos los artículos insumos más pedidos
    @Query("""
        SELECT new org.mija.elbuensaborback.application.dto.response.RankingArticuloResponse(
            a.denominacion,
            SUM(d.cantidad),
            SUM(d.subTotal)
        )
        FROM pedido_detalle d
        JOIN d.pedido p
        JOIN d.articulo a
        WHERE p.estadoEnum = 'TERMINADO'
          AND TYPE(a) = articulo_insumo
        GROUP BY a.id, a.denominacion
        ORDER BY SUM(d.cantidad) DESC
    """)
    List<RankingArticuloResponse> rankingArticulosInsumosMasPedidos();

    // 🔹 Entre fechas
    @Query("""
        SELECT new org.mija.elbuensaborback.application.dto.response.RankingArticuloResponse(
            a.denominacion,
            SUM(d.cantidad),
            SUM(d.subTotal)
        )
        FROM pedido_detalle d
        JOIN d.pedido p
        JOIN d.articulo a
        WHERE p.estadoEnum = 'TERMINADO'
          AND TYPE(a) = articulo_insumo
          AND p.fechaPedido BETWEEN :fechaInicio AND :fechaFin
        GROUP BY a.id, a.denominacion
        ORDER BY SUM(d.cantidad) DESC
    """)
    List<RankingArticuloResponse> rankingArticulosInsumosMasPedidosEntreFechas(
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin
    );

    // 🔹 Desde fecha
    @Query("""
        SELECT new org.mija.elbuensaborback.application.dto.response.RankingArticuloResponse(
            a.denominacion,
            SUM(d.cantidad),
            SUM(d.subTotal)
        )
        FROM pedido_detalle d
        JOIN d.pedido p
        JOIN d.articulo a
        WHERE p.estadoEnum = 'TERMINADO'
          AND TYPE(a) = articulo_insumo
          AND p.fechaPedido >= :fechaInicio
        GROUP BY a.id, a.denominacion
        ORDER BY SUM(d.cantidad) DESC
    """)
    List<RankingArticuloResponse> rankingArticulosInsumosMasPedidosDesdeFecha(
            @Param("fechaInicio") LocalDate fechaInicio
    );

    // 🔹 Hasta fecha
    @Query("""
        SELECT new org.mija.elbuensaborback.application.dto.response.RankingArticuloResponse(
            a.denominacion,
            SUM(d.cantidad),
            SUM(d.subTotal)
        )
        FROM pedido_detalle d
        JOIN d.pedido p
        JOIN d.articulo a
        WHERE p.estadoEnum = 'TERMINADO'
          AND TYPE(a) = articulo_insumo 
          AND p.fechaPedido <= :fechaFin
        GROUP BY a.id, a.denominacion
        ORDER BY SUM(d.cantidad) DESC
    """)
    List<RankingArticuloResponse> rankingArticulosInsumosMasPedidosHastaFecha(
            @Param("fechaFin") LocalDate fechaFin
    );
}

