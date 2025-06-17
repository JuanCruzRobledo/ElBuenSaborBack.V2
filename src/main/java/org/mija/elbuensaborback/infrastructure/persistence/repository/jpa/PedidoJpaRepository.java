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

    @Query("""
    SELECT new org.mija.elbuensaborback.application.dto.response.RankingClientesResponse(
        c.nombre,
        c.apellido,
        COUNT(p),
        SUM(p.total)
    )
    FROM pedido p
    JOIN p.cliente c
    WHERE p.estadoEnum = 'TERMINADO'
    GROUP BY c.id, c.nombre, c.apellido
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
    WHERE p.estadoEnum = 'TERMINADO'
    AND p.fechaPedido BETWEEN :fechaInicio AND :fechaFin
    GROUP BY c.id, c.nombre, c.apellido
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
    WHERE p.estadoEnum = 'TERMINADO'
    AND p.fechaPedido >= :fechaInicio
    GROUP BY c.id, c.nombre, c.apellido
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
    WHERE p.estadoEnum = 'TERMINADO'
    AND p.fechaPedido <= :fechaFin
    GROUP BY c.id, c.nombre, c.apellido
""")
    List<RankingClientesResponse> obtenerVentasClientesHastaFecha(
            @Param("fechaFin") LocalDate fechaFin
    );


}
