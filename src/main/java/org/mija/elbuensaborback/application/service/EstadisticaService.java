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
        // Obtener la fecha de ayer
        LocalDate ayer = LocalDate.now().minusDays(1);

        // Buscar los pedidos del día anterior que fueron ENTREGADOS
        List<PedidoEntity> pedidos = pedidoRepository.findTerminadosByFechaPedido(ayer);

        // Calcular el ingreso total sumando los totales de los pedidos
        BigDecimal ingreso = pedidos.stream()
                .map(PedidoEntity::getTotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calcular el costo total sumando los costos de los pedidos
        BigDecimal costo = pedidos.stream()
                .map(PedidoEntity::getCostoTotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calcular la ganancia como ingreso - costo
        BigDecimal ganancia = ingreso.subtract(costo);

        // Buscar si ya existe una estadística para ese día, si no, crear una nueva
        EstadisticaDiaria estadistica = estadisticaDiariaRepository
                .findByFecha(ayer)
                .orElse(new EstadisticaDiaria());

        // Establecer los valores calculados
        estadistica.setFecha(ayer);
        estadistica.setIngresoTotal(ingreso);
        estadistica.setCostoTotal(costo);
        estadistica.setGanancia(ganancia);

        // Guardar (crear o actualizar) la estadística en la base de datos
        estadisticaDiariaRepository.save(estadistica);
    }

    public void generarEstadisticasDelDiaActual() {
        // Obtener la fecha actual
        LocalDate hoy = LocalDate.now();

        // Buscar los pedidos del día actual que fueron ENTREGADOS
        List<PedidoEntity> pedidos = pedidoRepository.findTerminadosByFechaPedido(hoy);

        // Calcular el ingreso total
        BigDecimal ingreso = pedidos.stream()
                .map(PedidoEntity::getTotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calcular el costo total
        BigDecimal costo = pedidos.stream()
                .map(PedidoEntity::getCostoTotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calcular la ganancia como ingreso - costo
        BigDecimal ganancia = ingreso.subtract(costo);

        // Buscar si ya existe una estadística para ese día, si no, crear una nueva
        EstadisticaDiaria estadistica = estadisticaDiariaRepository
                .findByFecha(hoy)
                .orElse(new EstadisticaDiaria());

        // Establecer los valores calculados
        estadistica.setFecha(hoy);
        estadistica.setIngresoTotal(ingreso);
        estadistica.setCostoTotal(costo);
        estadistica.setGanancia(ganancia);

        // Guardar (crear o actualizar) la estadística en la base de datos
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

    public void generarEstadisticasDeTodosLosDias() {
        // Obtener todas las fechas únicas donde hubo pedidos entregados
        List<LocalDate> fechas = pedidoRepository.findFechasDePedidosEntregados();

        // Iterar por cada fecha
        for (LocalDate fecha : fechas) {
            // Obtener los pedidos ENTREGADOS de esa fecha
            List<PedidoEntity> pedidos = pedidoRepository.findTerminadosByFechaPedido(fecha);

            // Calcular ingreso total sumando los totales de los pedidos
            BigDecimal ingreso = pedidos.stream()
                    .map(PedidoEntity::getTotal)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Calcular costo total sumando los costos de los pedidos
            BigDecimal costo = pedidos.stream()
                    .map(PedidoEntity::getCostoTotal)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Calcular ganancia = ingreso - costo
            BigDecimal ganancia = ingreso.subtract(costo);

            // Buscar si ya existe una estadística para esa fecha, si no, crear una nueva
            EstadisticaDiaria estadistica = estadisticaDiariaRepository
                    .findByFecha(fecha)
                    .orElse(new EstadisticaDiaria());

            // Establecer los valores en la estadística
            estadistica.setFecha(fecha);
            estadistica.setIngresoTotal(ingreso);
            estadistica.setCostoTotal(costo);
            estadistica.setGanancia(ganancia);

            // Guardar (insertar o actualizar) en la base de datos
            estadisticaDiariaRepository.save(estadistica);
        }
    }

}