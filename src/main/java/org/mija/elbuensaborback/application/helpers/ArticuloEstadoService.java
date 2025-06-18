package org.mija.elbuensaborback.application.helpers;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.infrastructure.persistence.entity.*;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ArticuloEstadoService {

    private final PromocionDetalleRepositoryImpl promocionDetalleRepository;
    private final ArticuloPromocionRepositoryImpl articuloPromocionRepository;
    private final ArticuloManufacturadoRepositoryImpl articuloManufacturadoRepository;
    private final ArticuloInsumoRepositoryImpl articuloInsumoRepository;
    private final ArticuloManufacturadoDetalleRepositoryImpl articuloManufacturadoDetalleRepository;

    // ================== DESACTIVACIÓN =====================

    public void desactivarInsumoRecursivamente(ArticuloInsumoEntity insumo) {
        if (Boolean.FALSE.equals(insumo.getProductoActivo())) return;

        insumo.setProductoActivo(false);
        articuloInsumoRepository.save(insumo);

        // Desactivar manufacturados que lo usan
        articuloManufacturadoDetalleRepository.findByArticuloInsumo(insumo).stream()
                .map(ArticuloManufacturadoDetalleEntity::getArticuloManufacturado)
                .distinct()
                .forEach(this::desactivarManufacturadoRecursivamente);

        // Desactivar promociones que lo usan directamente
        obtenerPromocionesRelacionadas(insumo).forEach(this::desactivarPromocionRecursivamente);
    }

    public void desactivarManufacturadoRecursivamente(ArticuloManufacturadoEntity manufacturado) {
        if (Boolean.FALSE.equals(manufacturado.getProductoActivo())) return;

        manufacturado.setProductoActivo(false);
        articuloManufacturadoRepository.save(manufacturado);

        obtenerPromocionesRelacionadas(manufacturado).forEach(this::desactivarPromocionRecursivamente);
    }

    public void desactivarPromocionRecursivamente(ArticuloPromocionEntity promocion) {
        if (Boolean.FALSE.equals(promocion.getProductoActivo())) return;

        promocion.setProductoActivo(false);
        articuloPromocionRepository.save(promocion);

        // Desactivar promociones que la tengan como detalle
        obtenerPromocionesRelacionadas(promocion).stream()
                .filter(p -> !p.getId().equals(promocion.getId()))
                .forEach(this::desactivarPromocionRecursivamente);
    }

    // ================== REACTIVACIÓN =====================

    public void reactivarInsumoRecursivamente(ArticuloInsumoEntity insumo) {
        if (Boolean.TRUE.equals(insumo.getProductoActivo())) return;

        insumo.setProductoActivo(true);
        articuloInsumoRepository.save(insumo);

        // Reactivar manufacturados que lo usan (si todos los insumos están activos)
        articuloManufacturadoDetalleRepository.findByArticuloInsumo(insumo).stream()
                .map(ArticuloManufacturadoDetalleEntity::getArticuloManufacturado)
                .distinct()
                .forEach(this::reactivarManufacturadoRecursivamente);

        // Reactivar promociones que lo usen directamente (si todos sus artículos están activos)
        obtenerPromocionesRelacionadas(insumo).forEach(this::reactivarPromocionRecursivamente);
    }

    public void reactivarManufacturadoRecursivamente(ArticuloManufacturadoEntity manufacturado) {
        if (Boolean.TRUE.equals(manufacturado.getProductoActivo())) return;

        boolean todosInsumosActivos = Optional.ofNullable(manufacturado.getArticuloManufacturadoDetalle())
                .orElse(Collections.emptyList())
                .stream()
                .map(ArticuloManufacturadoDetalleEntity::getArticuloInsumo)
                .allMatch(i -> Boolean.TRUE.equals(i.getProductoActivo()));

        if (!todosInsumosActivos) return;

        manufacturado.setProductoActivo(true);
        articuloManufacturadoRepository.save(manufacturado);

        obtenerPromocionesRelacionadas(manufacturado).forEach(this::reactivarPromocionRecursivamente);
    }

    public void reactivarPromocionRecursivamente(ArticuloPromocionEntity promocion) {
        if (Boolean.TRUE.equals(promocion.getProductoActivo())) return;

        boolean todosArticulosActivos = Optional.ofNullable(promocion.getPromocionDetalle())
                .orElse(Collections.emptyList())
                .stream()
                .map(PromocionDetalleEntity::getArticulo)
                .allMatch(a -> Boolean.TRUE.equals(a.getProductoActivo()));

        if (!todosArticulosActivos) return;

        promocion.setProductoActivo(true);
        articuloPromocionRepository.save(promocion);

        obtenerPromocionesRelacionadas(promocion).stream()
                .filter(p -> !p.getId().equals(promocion.getId()))
                .forEach(this::reactivarPromocionRecursivamente);
    }

    // ================== UTILIDAD =====================

    private List<ArticuloPromocionEntity> obtenerPromocionesRelacionadas(ArticuloEntity articulo) {
        return promocionDetalleRepository.findByArticulo(articulo).stream()
                .map(PromocionDetalleEntity::getArticuloPromocion)
                .distinct()
                .toList();
    }
}
