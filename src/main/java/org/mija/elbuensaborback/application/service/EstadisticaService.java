package org.mija.elbuensaborback.application.service;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.response.RankingClientesResponse;
import org.mija.elbuensaborback.application.dto.response.RankingArticuloResponse;
import org.mija.elbuensaborback.domain.enums.EstadoEnum;
import org.mija.elbuensaborback.domain.enums.TipoEnvioEnum;
import org.mija.elbuensaborback.infrastructure.persistence.entity.*;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.DetallePedidoRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.PedidoRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.EstadisticaDiariaJpaRepository;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.PedidoJpaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EstadisticaService {

    private final PedidoRepositoryImpl pedidoRepository;
    private final PedidoJpaRepository pedidoJpaRepository;
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

    public List<EstadisticaDiaria> obtenerEstadisticasPorRangoFlexible(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio != null && fechaFin != null) {
            return estadisticaDiariaRepository.findByFechaBetween(fechaInicio, fechaFin);
        } else if (fechaInicio != null) {
            return estadisticaDiariaRepository.findByFechaGreaterThanEqual(fechaInicio);
        } else if (fechaFin != null) {
            return estadisticaDiariaRepository.findByFechaLessThanEqual(fechaFin);
        } else {
            return estadisticaDiariaRepository.findAll();
        }
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

    public List<RankingArticuloResponse> rankingInsumos(LocalDate fechaInicio, LocalDate fechaFin) {
        List<RankingArticuloResponse> ranking;

        if (fechaInicio != null && fechaFin != null) {
            ranking = detallePedidoRepository.rankingArticulosInsumosMasPedidosEntreFechas(fechaInicio, fechaFin);
        } else if (fechaInicio != null) {
            ranking = detallePedidoRepository.rankingArticulosInsumosMasPedidosDesdeFecha(fechaInicio);
        } else if (fechaFin != null) {
            ranking = detallePedidoRepository.rankingArticulosInsumosMasPedidosHastaFecha(fechaFin);
        } else {
            ranking = detallePedidoRepository.rankingArticulosInsumosMasPedidos();
        }

        // Buscar pedidos entregados en el rango deseado
        List<PedidoEntity> pedidos = obtenerPedidosEntregadosEntreFechas(fechaInicio, fechaFin);
        agregarArticulosDesdePromociones(ranking, pedidos, ArticuloInsumoEntity.class);

        return aplicarDescuentoSoloTakeaway(ranking);
    }

    public List<RankingArticuloResponse> rankingManufacturados(LocalDate fechaInicio, LocalDate fechaFin) {
        List<RankingArticuloResponse> ranking;

        if (fechaInicio != null && fechaFin != null) {
            ranking = detallePedidoRepository.rankingArticulosManufacturadosMasPedidosEntreFechas(fechaInicio, fechaFin);
        } else if (fechaInicio != null) {
            ranking = detallePedidoRepository.rankingArticulosManufacturadosMasPedidosDesdeFecha(fechaInicio);
        } else if (fechaFin != null) {
            ranking = detallePedidoRepository.rankingArticulosManufacturadosMasPedidosHastaFecha(fechaFin);
        } else {
            ranking = detallePedidoRepository.rankingArticulosManufacturadosMasPedidos();
        }

        List<PedidoEntity> pedidos = obtenerPedidosEntregadosEntreFechas(fechaInicio, fechaFin);
        agregarArticulosDesdePromociones(ranking, pedidos, ArticuloManufacturadoEntity.class);

        return aplicarDescuentoSoloTakeaway(ranking);
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

    //========================METODOS DE AYUDA ================================

    private void agregarArticulosDesdePromociones(
            List<RankingArticuloResponse> ranking,
            List<PedidoEntity> pedidos,
            Class<? extends ArticuloEntity> tipoArticulo
    ) {
        Map<String, RankingArticuloResponse> mapa = new HashMap<>();

        // Cargar ranking actual al mapa
        for (RankingArticuloResponse r : ranking) {
            String clave = r.denominacion() + r.tipoEnvioEnum();
            mapa.put(clave, r);
        }

        for (PedidoEntity pedido : pedidos) {
            if (pedido.getEstadoEnum() != EstadoEnum.ENTREGADO) continue;

            for (DetallePedidoEntity detalle : pedido.getListaDetalle()) {
                ArticuloEntity articulo = detalle.getArticulo();

                if (articulo instanceof ArticuloPromocionEntity promo) {
                    for (PromocionDetalleEntity pd : promo.getPromocionDetalle()) {
                        ArticuloEntity articuloPromo = pd.getArticulo();

                        if (tipoArticulo.isInstance(articuloPromo)) {
                            String denominacion = articuloPromo.getDenominacion();
                            TipoEnvioEnum tipoEnvio = pedido.getTipoEnvioEnum();
                            String clave = denominacion + tipoEnvio;

                            int cantidadTotal = detalle.getCantidad() * pd.getCantidad();
                            BigDecimal subtotal = articuloPromo.getPrecioVenta().multiply(BigDecimal.valueOf(cantidadTotal));

                            RankingArticuloResponse existente = mapa.get(clave);

                            if (existente != null) {
                                Long nuevaCantidad = existente.cantidadTotal() + cantidadTotal;
                                BigDecimal nuevoSubTotal = existente.totalRecaudado().add(subtotal);

                                mapa.put(clave, new RankingArticuloResponse(
                                        denominacion,
                                        nuevaCantidad,
                                        nuevoSubTotal,
                                        tipoEnvio
                                ));
                            } else {
                                mapa.put(clave, new RankingArticuloResponse(
                                        denominacion,
                                        (long) cantidadTotal,
                                        subtotal,
                                        tipoEnvio
                                ));
                            }
                        }
                    }
                }
            }
        }

        // Reemplazar el contenido original con los nuevos objetos actualizados
        ranking.clear();
        ranking.addAll(mapa.values());
    }

    // Aplica descuento del 10% si el artículo fue vendido con algún pedido tipo TAKEAWAY
    private List<RankingArticuloResponse> aplicarDescuentoSoloTakeaway(List<RankingArticuloResponse> ranking) {
        Map<String, RankingArticuloResponse> resultado = new HashMap<>();

        for (RankingArticuloResponse item : ranking) {
            BigDecimal subtotal = item.totalRecaudado();

            // Aplica descuento si el tipo de envío fue TAKEAWAY
            if (item.tipoEnvioEnum() == TipoEnvioEnum.TAKEAWAY) {
                subtotal = subtotal.multiply(BigDecimal.valueOf(0.9));
            }

            resultado.merge(
                    item.denominacion(),
                    new RankingArticuloResponse(
                            item.denominacion(),
                            item.cantidadTotal(),
                            subtotal,
                            null // ya no usamos tipo de envío final
                    ),
                    (existente, nuevo) -> new RankingArticuloResponse(
                            existente.denominacion(),
                            existente.cantidadTotal() + nuevo.cantidadTotal(),
                            existente.totalRecaudado().add(nuevo.totalRecaudado()),
                            null
                    )
            );
        }

        return resultado.values().stream()
                .sorted(Comparator.comparing(RankingArticuloResponse::cantidadTotal).reversed())
                .toList();
    }

    private List<PedidoEntity> obtenerPedidosEntregadosEntreFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio != null && fechaFin != null) {
            return pedidoJpaRepository.findByEstadoEnumAndFechaPedidoBetween(EstadoEnum.ENTREGADO, fechaInicio, fechaFin);
        } else if (fechaInicio != null) {
            return pedidoJpaRepository.findByEstadoEnumAndFechaPedidoAfter(EstadoEnum.ENTREGADO, fechaInicio.minusDays(1));
        } else if (fechaFin != null) {
            return pedidoJpaRepository.findByEstadoEnumAndFechaPedidoBefore(EstadoEnum.ENTREGADO, fechaFin.plusDays(1));
        } else {
            return pedidoJpaRepository.findByEstadoEnum(EstadoEnum.ENTREGADO);
        }
    }
}