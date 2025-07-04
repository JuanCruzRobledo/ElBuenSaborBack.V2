package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.application.dto.response.RankingClientesResponse;
import org.mija.elbuensaborback.domain.enums.EstadoEnum;
import org.mija.elbuensaborback.infrastructure.persistence.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PedidoJpaRepository extends JpaRepository<PedidoEntity, Long> {

    List<PedidoEntity> findByClienteId(Long clienteId);

    @Query("SELECT p FROM pedido p JOIN FETCH p.cliente WHERE p.id = :id")
    Optional<PedidoEntity> findByIdWithCliente(@Param("id") Long id);

    List<PedidoEntity> findByFechaPedidoBetween(LocalDateTime desde, LocalDateTime hasta);

    List<PedidoEntity> findByFechaPedidoAndEstadoEnum(LocalDate fechaPedido, EstadoEnum estadoEnum);

    List<PedidoEntity> findAllByFechaPedido(LocalDate fecha);

    @Query("SELECT DISTINCT p.fechaPedido FROM pedido p WHERE p.estadoEnum = 'ENTREGADO'")
    List<LocalDate> findFechasDePedidosEntregados();

    // ======================= TODOS LOS PEDIDOS ENTREGADO ========================//
    @Query("""
    SELECT new org.mija.elbuensaborback.application.dto.response.RankingClientesResponse(
        c.nombre,
        c.apellido,
        COUNT(p),
        SUM(p.total)
    )
    FROM pedido p
    JOIN p.cliente c
    WHERE p.estadoEnum = 'ENTREGADO'
    GROUP BY c.id, c.nombre, c.apellido
    ORDER BY SUM(p.total) DESC
""")
    List<RankingClientesResponse> obtenerVentasClientesFinalizados();

    @Query("""
    SELECT new org.mija.elbuensaborback.application.dto.response.RankingClientesResponse(
        c.nombre,
        c.apellido,
        COUNT(p),
        SUM(p.total)
    )
    FROM pedido p
    JOIN p.cliente c
    WHERE p.estadoEnum = 'ENTREGADO'
    AND p.fechaPedido BETWEEN :fechaInicio AND :fechaFin
    GROUP BY c.id, c.nombre, c.apellido
    ORDER BY SUM(p.total) DESC
""")
    List<RankingClientesResponse> obtenerVentasClientesPorRango(
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin
    );

    @Query("""
    SELECT new org.mija.elbuensaborback.application.dto.response.RankingClientesResponse(
        c.nombre,
        c.apellido,
        COUNT(p),
        SUM(p.total)
    )
    FROM pedido p
    JOIN p.cliente c
    WHERE p.estadoEnum = 'ENTREGADO'
    AND p.fechaPedido >= :fechaInicio
    GROUP BY c.id, c.nombre, c.apellido
    ORDER BY SUM(p.total) DESC
""")
    List<RankingClientesResponse> obtenerVentasClientesDesdeFecha(
            @Param("fechaInicio") LocalDate fechaInicio
    );

    @Query("""
    SELECT new org.mija.elbuensaborback.application.dto.response.RankingClientesResponse(
        c.nombre,
        c.apellido,
        COUNT(p),
        SUM(p.total)
    )
    FROM pedido p
    JOIN p.cliente c
    WHERE p.estadoEnum = 'ENTREGADO'
    AND p.fechaPedido <= :fechaFin
    GROUP BY c.id, c.nombre, c.apellido
    ORDER BY SUM(p.total) DESC
""")
    List<RankingClientesResponse> obtenerVentasClientesHastaFecha(
            @Param("fechaFin") LocalDate fechaFin
    );

    // ======================= TODOS LOS PEDIDOS ========================//

    @Query("""
    SELECT p
    FROM pedido p
    JOIN p.cliente c
    WHERE p.fechaPedido BETWEEN :fechaInicio AND :fechaFin
""")
    List<PedidoEntity> obtenerPedidosPorRango(
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin
    );

    @Query("""
    SELECT p
    FROM pedido p
    JOIN p.cliente c
    WHERE p.fechaPedido >= :fechaInicio
    GROUP BY c.id, c.nombre, c.apellido
""")
    List<PedidoEntity> obtenerPedidosDesdeFecha(
            @Param("fechaInicio") LocalDate fechaInicio
    );

    @Query("""
    SELECT p
    FROM pedido p
    JOIN p.cliente c
    WHERE p.fechaPedido <= :fechaFin
    GROUP BY c.id, c.nombre, c.apellido
""")
    List<PedidoEntity> obtenerPedidosHastaFecha(
            @Param("fechaFin") LocalDate fechaFin
    );

    //===================AUXILIARES====================

    List<PedidoEntity> findByEstadoEnumAndFechaPedidoBetween(EstadoEnum estado, LocalDate inicio, LocalDate fin);
    List<PedidoEntity> findByEstadoEnumAndFechaPedidoAfter(EstadoEnum estado, LocalDate inicio);
    List<PedidoEntity> findByEstadoEnumAndFechaPedidoBefore(EstadoEnum estado, LocalDate fin);
    List<PedidoEntity> findByEstadoEnum(EstadoEnum estado);
}
