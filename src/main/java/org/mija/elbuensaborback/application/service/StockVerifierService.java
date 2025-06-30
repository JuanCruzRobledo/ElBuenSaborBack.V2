package org.mija.elbuensaborback.application.service;

import org.mija.elbuensaborback.domain.exceptions.StockInsuficienteException;
import org.mija.elbuensaborback.infrastructure.persistence.entity.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class StockVerifierService {

    public void verificarStock(PedidoEntity pedido) {
        // Mapa temporal para acumular el uso de insumos por insumo
        Map<ArticuloInsumoEntity, Double> insumosUsados = new HashMap<>();
        Map<ArticuloInsumoEntity, String> insumoArticuloPadre = new HashMap<>();

        for (DetallePedidoEntity detalle : pedido.getListaDetalle()) {
            String nombreArticulo = detalle.getArticulo().getDenominacion();
            int cantidad = detalle.getCantidad();
            ArticuloEntity articulo = detalle.getArticulo();

            if (articulo instanceof ArticuloInsumoEntity insumo) {
                acumularInsumo(insumosUsados, insumo, cantidad);
                insumoArticuloPadre.put(insumo, nombreArticulo);
            } else if (articulo instanceof ArticuloManufacturadoEntity manufacturado) {
                acumularManufacturado(insumosUsados, insumoArticuloPadre, manufacturado, cantidad, nombreArticulo);
            } else if (articulo instanceof ArticuloPromocionEntity promo) {
                acumularPromocion(insumosUsados, insumoArticuloPadre, promo, cantidad, nombreArticulo);
            }
        }

        for (Map.Entry<ArticuloInsumoEntity, Double> entry : insumosUsados.entrySet()) {
            ArticuloInsumoEntity insumo = entry.getKey();
            double cantidadRequerida = entry.getValue();
            double disponible = insumo.getStockActual() - insumo.getStockReservado();

            if (disponible < cantidadRequerida) {
                String articuloPadre = insumoArticuloPadre.getOrDefault(insumo, "el pedido");
                throw new StockInsuficienteException(
                        "No contamos con stock suficiente del insumo '" + insumo.getDenominacion() + "'. Disminuye la cantidad o espera reposición."
                );
            }
        }
    }

    private void acumularInsumo(Map<ArticuloInsumoEntity, Double> mapa, ArticuloInsumoEntity insumo, double cantidad) {
        mapa.put(insumo, mapa.getOrDefault(insumo, 0.0) + cantidad);
    }

    private void acumularManufacturado(
            Map<ArticuloInsumoEntity, Double> mapa,
            Map<ArticuloInsumoEntity, String> padres,
            ArticuloManufacturadoEntity manufacturado,
            int cantidad,
            String articuloPadre
    ) {
        for (ArticuloManufacturadoDetalleEntity detalle : manufacturado.getArticuloManufacturadoDetalle()) {
            ArticuloInsumoEntity insumo = detalle.getArticuloInsumo();
            double requerido = detalle.getCantidad() * cantidad;
            acumularInsumo(mapa, insumo, requerido);
            padres.put(insumo, articuloPadre);
        }
    }

    private void acumularPromocion(
            Map<ArticuloInsumoEntity, Double> mapa,
            Map<ArticuloInsumoEntity, String> padres,
            ArticuloPromocionEntity promo,
            int cantidadPromo,
            String articuloPadre
    ) {
        for (int i = 0; i < cantidadPromo; i++) {
            for (PromocionDetalleEntity detalle : promo.getPromocionDetalle()) {
                ArticuloEntity art = detalle.getArticulo();
                int cantidadDetalle = detalle.getCantidad();

                if (art instanceof ArticuloManufacturadoEntity manufacturado) {
                    acumularManufacturado(mapa, padres, manufacturado, cantidadDetalle, articuloPadre);
                } else if (art instanceof ArticuloInsumoEntity insumo) {
                    acumularInsumo(mapa, insumo, cantidadDetalle);
                    padres.put(insumo, articuloPadre);
                }
            }
        }
    }
}