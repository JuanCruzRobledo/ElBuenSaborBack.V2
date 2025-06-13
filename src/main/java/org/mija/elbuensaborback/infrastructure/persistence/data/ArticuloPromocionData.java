package org.mija.elbuensaborback.infrastructure.persistence.data;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloPromocionEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.PromocionDetalleEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.SucursalEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ArticuloJpaRepository;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ArticuloPromocionJpaRepository;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.SucursalJpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticuloPromocionData {

    private final ArticuloPromocionJpaRepository articuloPromocionRepository;
    private final ArticuloJpaRepository articuloRepository;
    private final SucursalJpaRepository sucursalRepository;

    public void initPromociones() {
        SucursalEntity sucursal = sucursalRepository.findById(1L).orElse(null);

        ArticuloEntity cocaCola = articuloRepository.findById(23L)
                .orElseThrow(() -> new RuntimeException("No se encontró el artículo Coca Cola 500ml"));

        ArticuloEntity bbq = articuloRepository.findById(25L)
                .orElseThrow(() -> new RuntimeException("No se encontró el artículo BBQ"));

        ArticuloPromocionEntity promo = ArticuloPromocionEntity.builder()
                .denominacion("Combo BBQ + Coca Cola")
                .descripcionDescuento("Incluye hamburguesa BBQ y Coca Cola 500ml")
                //Aqui deberia calcular el precio venta dentro de un metodo
                // en promocion donde sume todos los precios venta de los articulos de la promo
                .precioVenta(new BigDecimal("9500.00"))
                .precioPromocional(new BigDecimal("9500.00"))
                .productoActivo(true)
                .tiempoEstimadoMinutos(25)
                .fechaDesde(LocalDate.now())
                .fechaHasta(LocalDate.now().plusMonths(12))
                .horaDesde(LocalTime.of(0, 0))
                .horaHasta(LocalTime.of(23, 0))
                .sucursal(sucursal)
                .build();

        // Crear los detalles de la promoción
        PromocionDetalleEntity detalleCoca = PromocionDetalleEntity.builder()
                .cantidad(1)
                .articulo(cocaCola)
                .articuloPromocion(promo)
                .build();

        PromocionDetalleEntity detalleBBQ = PromocionDetalleEntity.builder()
                .cantidad(1)
                .articulo(bbq)
                .articuloPromocion(promo)
                .build();

        // Asociar detalles a la promoción
        promo.setPromocionDetalle(List.of(detalleCoca, detalleBBQ));

        articuloPromocionRepository.save(promo);
    }
}
