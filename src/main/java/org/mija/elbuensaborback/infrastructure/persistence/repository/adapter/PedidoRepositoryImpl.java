package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;


import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.response.RankingClientesResponse;
import org.mija.elbuensaborback.domain.enums.EstadoEnum;
import org.mija.elbuensaborback.domain.repository.PedidoRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.PedidoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.PedidoJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PedidoRepositoryImpl implements PedidoRepositoryPort {
    private final PedidoJpaRepository pedidoJpaRepository;

    @Override
    public Optional<PedidoEntity> findById(Long aLong) {
        return pedidoJpaRepository.findById(aLong);
    }

    @Override
    public List<PedidoEntity> findAll() {
        return pedidoJpaRepository.findAll();
    }

    public List<PedidoEntity> findAllDiarios() {
        return pedidoJpaRepository.findAllByFechaPedido(LocalDate.now());
    }

    public List<PedidoEntity> findAllByCliente(Long id) {
        return pedidoJpaRepository.findByClienteId(id);
    }

    public Optional<PedidoEntity> findByIdConCliente(Long aLong) {
        return pedidoJpaRepository.findByIdWithCliente(aLong);
    }

    @Override
    public PedidoEntity save(PedidoEntity entity) {
        return pedidoJpaRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        pedidoJpaRepository.deleteById(aLong);
    }

    @Override
    public List<PedidoEntity> saveAll(List<PedidoEntity> entities) {
        return List.of();
    }

    public List<PedidoEntity> findByFechaPedidoBetween(LocalDateTime desde, LocalDateTime hasta) {
        return pedidoJpaRepository.findByFechaPedidoBetween(desde, hasta);
    }

    public List<PedidoEntity> findTerminadosByFechaPedido(LocalDate fechaPedido){
        return pedidoJpaRepository.findByFechaPedidoAndEstadoEnum(fechaPedido, EstadoEnum.ENTREGADO);
    }

    public List<RankingClientesResponse> obtenerVentasClientesFinalizados() {
        return pedidoJpaRepository.obtenerVentasClientesFinalizados();
    }

    public List<RankingClientesResponse> obtenerVentasClientesPorRango(LocalDate fechaInicio, LocalDate fechaFin) {
        return pedidoJpaRepository.obtenerVentasClientesPorRango(fechaInicio, fechaFin);
    }

    public List<RankingClientesResponse> obtenerVentasClientesDesdeFecha(LocalDate fechaInicio) {
        return pedidoJpaRepository.obtenerVentasClientesDesdeFecha(fechaInicio);
    }

    public List<RankingClientesResponse> obtenerVentasClientesHastaFecha(LocalDate fechaFin) {
        return pedidoJpaRepository.obtenerVentasClientesHastaFecha(fechaFin);
    }

    public List<PedidoEntity> obtenerPedidosPorRango(LocalDate fechaInicio, LocalDate fechaFin) {
        return pedidoJpaRepository.obtenerPedidosPorRango(fechaInicio, fechaFin);
    }

    public List<PedidoEntity> obtenerPedidosDesdeFecha(LocalDate fechaInicio) {
        return pedidoJpaRepository.obtenerPedidosDesdeFecha(fechaInicio);
    }

    public List<PedidoEntity> obtenerPedidosHastaFecha(LocalDate fechaFin) {
        return pedidoJpaRepository.obtenerPedidosHastaFecha(fechaFin);
    }

    public List<LocalDate> findFechasDePedidosEntregados() {
        return pedidoJpaRepository.findFechasDePedidosEntregados();
    }
}
