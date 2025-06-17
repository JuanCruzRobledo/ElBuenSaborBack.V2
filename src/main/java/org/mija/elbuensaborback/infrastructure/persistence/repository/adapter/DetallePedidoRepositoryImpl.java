package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.response.RankingManufacturadoResponse;
import org.mija.elbuensaborback.domain.repository.DetallePedidoRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.DetallePedidoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.DetallePedidoJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DetallePedidoRepositoryImpl implements DetallePedidoRepositoryPort {
    private final DetallePedidoJpaRepository detallePedidoJpaRepository;

    @Override
    public Optional<DetallePedidoEntity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<DetallePedidoEntity> findAll() {
        return List.of();
    }

    @Override
    public DetallePedidoEntity save(DetallePedidoEntity entity) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<DetallePedidoEntity> saveAll(List<DetallePedidoEntity> entities) {
        return List.of();
    }

    public List<RankingManufacturadoResponse> rankingArticulosMasPedidos() {
        return detallePedidoJpaRepository.rankingArticulosMasPedidos();
    }

    public List<RankingManufacturadoResponse> rankingArticulosMasPedidosEntreFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return detallePedidoJpaRepository.rankingArticulosMasPedidosEntreFechas(fechaInicio,fechaFin);
    }
    public List<RankingManufacturadoResponse> rankingArticulosMasPedidosDesdeFecha(LocalDate fechaInicio) {
        return detallePedidoJpaRepository.rankingArticulosMasPedidosDesdeFecha(fechaInicio);
    }
    public List<RankingManufacturadoResponse> rankingArticulosMasPedidosHastaFecha(LocalDate fechaFin) {
        return detallePedidoJpaRepository.rankingArticulosMasPedidosHastaFecha(fechaFin);
    }

}
