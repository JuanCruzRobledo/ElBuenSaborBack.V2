package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.response.RankingArticuloResponse;
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

    // =========================== ARTICULO MANUFACTURADO =============================

    public List<RankingArticuloResponse> rankingArticulosManufacturadosMasPedidos() {
        return detallePedidoJpaRepository.rankingArticulosManufacturadosMasPedidos();
    }

    public List<RankingArticuloResponse> rankingArticulosManufacturadosMasPedidosEntreFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return detallePedidoJpaRepository.rankingArticulosManufacturadosMasPedidosEntreFechas(fechaInicio,fechaFin);
    }
    public List<RankingArticuloResponse> rankingArticulosManufacturadosMasPedidosDesdeFecha(LocalDate fechaInicio) {
        return detallePedidoJpaRepository.rankingArticulosManufacturadosMasPedidosDesdeFecha(fechaInicio);
    }
    public List<RankingArticuloResponse> rankingArticulosManufacturadosMasPedidosHastaFecha(LocalDate fechaFin) {
        return detallePedidoJpaRepository.rankingArticulosManufacturadosMasPedidosHastaFecha(fechaFin);
    }

    // =========================== ARTICULO INSUMO =============================

    public List<RankingArticuloResponse> rankingArticulosInsumosMasPedidos() {
        return detallePedidoJpaRepository.rankingArticulosInsumosMasPedidos();
    }

    public List<RankingArticuloResponse> rankingArticulosInsumosMasPedidosEntreFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return detallePedidoJpaRepository.rankingArticulosInsumosMasPedidosEntreFechas(fechaInicio,fechaFin);
    }
    public List<RankingArticuloResponse> rankingArticulosInsumosMasPedidosDesdeFecha(LocalDate fechaInicio) {
        return detallePedidoJpaRepository.rankingArticulosInsumosMasPedidosDesdeFecha(fechaInicio);
    }
    public List<RankingArticuloResponse> rankingArticulosInsumosMasPedidosHastaFecha(LocalDate fechaFin) {
        return detallePedidoJpaRepository.rankingArticulosInsumosMasPedidosHastaFecha(fechaFin);
    }
}
