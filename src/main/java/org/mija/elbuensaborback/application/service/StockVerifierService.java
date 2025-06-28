package org.mija.elbuensaborback.application.service;

import org.mija.elbuensaborback.domain.exceptions.StockInsuficienteException;
import org.mija.elbuensaborback.infrastructure.persistence.entity.*;
import org.springframework.stereotype.Service;

@Service
public class StockVerifierService {

    public void verificarStock(PedidoEntity pedido) {
        for (DetallePedidoEntity detalle : pedido.getListaDetalle()) {
            String nombreArticulo = detalle.getArticulo().getDenominacion();
            int cantidad = detalle.getCantidad();

            ArticuloEntity articulo = detalle.getArticulo();

            if (articulo instanceof ArticuloInsumoEntity insumo) {
                verificarInsumo(insumo, cantidad, nombreArticulo);
            } else if (articulo instanceof ArticuloManufacturadoEntity manufacturado) {
                verificarManufacturado(manufacturado, cantidad, nombreArticulo);
            } else if (articulo instanceof ArticuloPromocionEntity promo) {
                verificarPromocion(promo, cantidad, nombreArticulo);
            }
        }
    }

    private void verificarInsumo(ArticuloInsumoEntity insumo, double cantidad, String articuloPadre) {
        if (insumo.getStockActual() < cantidad) {
            throw new StockInsuficienteException("No contamos con Stock para el articulo "+ articuloPadre + " .Prueba disminuyendo la cantidad");
        }
    }

    private void verificarManufacturado(ArticuloManufacturadoEntity manufacturado, int cantidad, String articuloPadre) {
        for (ArticuloManufacturadoDetalleEntity detalle : manufacturado.getArticuloManufacturadoDetalle()) {
            ArticuloInsumoEntity insumo = detalle.getArticuloInsumo();
            double requerido = detalle.getCantidad() * cantidad;
            verificarInsumo(insumo, requerido, articuloPadre);
        }
    }

    private void verificarPromocion(ArticuloPromocionEntity promo, int cantidad, String articuloPadre) {
        for (int i = 0; i < cantidad; i++) {
            for (PromocionDetalleEntity detalle : promo.getPromocionDetalle()) {
                ArticuloEntity art = detalle.getArticulo();
                int cantidadDetalle = detalle.getCantidad();

                if (art instanceof ArticuloManufacturadoEntity manufacturado) {
                    verificarManufacturado(manufacturado, cantidadDetalle, articuloPadre);
                } else if (art instanceof ArticuloInsumoEntity insumo) {
                    verificarInsumo(insumo, cantidadDetalle, articuloPadre);
                }
            }
        }
    }
}

