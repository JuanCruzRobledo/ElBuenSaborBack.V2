package org.mija.elbuensaborback.application.service;

import org.mija.elbuensaborback.infrastructure.persistence.entity.EstadisticaDiaria;
import org.mija.elbuensaborback.infrastructure.persistence.entity.PedidoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.PedidoRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.EstadisticaDiariaJpaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class EstadisticaService {

    private final PedidoRepositoryImpl pedidoRepository;

    private final EstadisticaDiariaJpaRepository estadisticaDiariaRepository;

    public EstadisticaService(PedidoRepositoryImpl pedidoRepository, EstadisticaDiariaJpaRepository estadisticaDiariaRepository) {
        this.pedidoRepository = pedidoRepository;
        this.estadisticaDiariaRepository = estadisticaDiariaRepository;
    }


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
}