package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.ClienteEntity;
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

    List<PedidoEntity> findByFechaPedido(LocalDate fechaPedido);
}
