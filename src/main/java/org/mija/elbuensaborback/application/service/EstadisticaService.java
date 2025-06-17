package org.mija.elbuensaborback.application.service;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.response.RankingClientesResponse;
import org.mija.elbuensaborback.application.dto.response.RankingArticuloResponse;
import org.mija.elbuensaborback.infrastructure.persistence.entity.EstadisticaDiaria;
import org.mija.elbuensaborback.infrastructure.persistence.entity.PedidoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.DetallePedidoRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.PedidoRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.EstadisticaDiariaJpaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EstadisticaService {

    private final PedidoRepositoryImpl pedidoRepository;
    private final DetallePedidoRepositoryImpl detallePedidoRepository;
    private final EstadisticaDiariaJpaRepository estadisticaDiariaRepository;

    // Ejecuta todos los días a las 2:00 AM
    @Scheduled(cron = "0 0 2 * * ?")
    public void generarEstadisticasDelDiaAnterior() {
        LocalDate ayer = LocalDate.now().minusDays(1);

        // Consultar pedidos del día anterior
        List<PedidoEntity> pedidos = pedidoRepository.findByFechaPedido(ayer);

        // Calcular ingreso total
        BigDecimal ingreso = pedidos.stream()
                .map(PedidoEntity::getTotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calcular costo total
        BigDecimal costo = pedidos.stream()
                .map(PedidoEntity::getCostoTotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calcular ganancia
        BigDecimal ganancia = ingreso.subtract(costo);

        // Crear y guardar la estadística
        EstadisticaDiaria estadistica = new EstadisticaDiaria();
        estadistica.setFecha(ayer);
        estadistica.setIngresoTotal(ingreso);
        estadistica.setCostoTotal(costo);
        estadistica.setGanancia(ganancia);

        estadisticaDiariaRepository.save(estadistica);
    }

    public void generarEstadisticasDelDiaActual() {
        LocalDate hoy = LocalDate.now();

        List<PedidoEntity> pedidos = pedidoRepository.findByFechaPedido(hoy);

        BigDecimal ingreso = pedidos.stream()
                .map(PedidoEntity::getTotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal costo = pedidos.stream()
                .map(PedidoEntity::getCostoTotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal ganancia = ingreso.subtract(costo);

        EstadisticaDiaria estadistica = new EstadisticaDiaria();
        estadistica.setFecha(hoy);
        estadistica.setIngresoTotal(ingreso);
        estadistica.setCostoTotal(costo);
        estadistica.setGanancia(ganancia);

        estadisticaDiariaRepository.save(estadistica);
    }

    public List<EstadisticaDiaria> obtenerEstadisticasPorRango(LocalDate fechaInicio, LocalDate fechaFin) {
        return estadisticaDiariaRepository.findByFechaBetween(fechaInicio, fechaFin);
    }


    public List<RankingClientesResponse> rankingClientes(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio != null && fechaFin != null) {
            return pedidoRepository.obtenerVentasClientesPorRango(fechaInicio, fechaFin);
        } else if (fechaInicio != null) {
            return pedidoRepository.obtenerVentasClientesDesdeFecha(fechaInicio);
        } else if (fechaFin != null) {
            return pedidoRepository.obtenerVentasClientesHastaFecha(fechaFin);
        } else {
            return pedidoRepository.obtenerVentasClientesFinalizados();
        }
    }

    public List<RankingArticuloResponse> rankingManufacturados(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio != null && fechaFin != null) {
            return detallePedidoRepository.rankingArticulosManufacturadosMasPedidosEntreFechas(fechaInicio, fechaFin);
        } else if (fechaInicio != null) {
            return detallePedidoRepository.rankingArticulosManufacturadosMasPedidosDesdeFecha(fechaInicio);
        } else if (fechaFin != null) {
            return detallePedidoRepository.rankingArticulosManufacturadosMasPedidosHastaFecha(fechaFin);
        } else {
            return detallePedidoRepository.rankingArticulosManufacturadosMasPedidos();
        }
    }

    public List<RankingArticuloResponse> rankingInsumos(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio != null && fechaFin != null) {
            return detallePedidoRepository.rankingArticulosInsumosMasPedidosEntreFechas(fechaInicio, fechaFin);
        } else if (fechaInicio != null) {
            return detallePedidoRepository.rankingArticulosInsumosMasPedidosDesdeFecha(fechaInicio);
        } else if (fechaFin != null) {
            return detallePedidoRepository.rankingArticulosInsumosMasPedidosHastaFecha(fechaFin);
        } else {
            return detallePedidoRepository.rankingArticulosInsumosMasPedidos();
        }
    }

}