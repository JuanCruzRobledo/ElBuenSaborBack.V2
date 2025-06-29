package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity(name = "ARTICULO_PROMOCION")
public class ArticuloPromocionEntity extends ArticuloEntity {

    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private LocalTime horaDesde;
    private LocalTime horaHasta;
    private String descripcion;
    //precioTotal: suma de los productos de la promoción (sería como el precio mínimo de la promoción)
    //precioVenta: precio de venta, con el descuento de la promoción aplicado
    private BigDecimal precioTotal;

    @OneToMany(mappedBy = "articuloPromocion", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<PromocionDetalleEntity> promocionDetalle;


    public void tiempoEstimadoCalculado(int tiempoBase){
        Integer max = 0;
        for (PromocionDetalleEntity detalle : this.getPromocionDetalle()) {
            if (detalle.getArticulo().getTiempoEstimadoMinutos() > max) {
                max = detalle.getArticulo().getTiempoEstimadoMinutos();
            }
        }
        this.setTiempoEstimadoMinutos(max + tiempoBase);
    }

    //Suma del precio de costo de todos los Articulos de la promocion
    public void calcularPrecioCosto(){
        BigDecimal costoTotal = BigDecimal.ZERO;

        for (PromocionDetalleEntity detalle : this.getPromocionDetalle()) {
            BigDecimal precio = detalle.getArticulo().getPrecioCosto();
            BigDecimal cantidad = BigDecimal.valueOf(detalle.getCantidad());
            BigDecimal precioPorCantidad = precio.multiply(cantidad);
            costoTotal = costoTotal.add(precioPorCantidad);
        }
        setPrecioCosto(costoTotal);
    }

    //Precio que va a valer la promocion
    public void calcularPrecioVenta(BigDecimal precioVentaManual) {
        if (getPrecioTotal() != null && getMargen() != null) {
            if (getMargen() == 0f) {
                // No hay descuento, se vende al precio total
                setPrecioVenta(getPrecioTotal());
            } else {
                // Aplicar margen de descuento
                BigDecimal descuento = BigDecimal.valueOf(getMargen())
                        .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
                BigDecimal nuevoPrecioVenta = getPrecioTotal().multiply(BigDecimal.ONE.subtract(descuento));
                setPrecioVenta(nuevoPrecioVenta);
            }
        } else {
            // Si no hay margen
            setPrecioVenta(precioVentaManual);
        }
    }

    //Precio de la suma del precio Venta de todos los Articulos de la promocion
    public void calcularPrecioTotal(){
        BigDecimal ventaTotal = BigDecimal.ZERO;

        for (PromocionDetalleEntity detalle : this.getPromocionDetalle()) {
            BigDecimal precio = detalle.getArticulo().getPrecioVenta();
            BigDecimal cantidad = BigDecimal.valueOf(detalle.getCantidad());
            BigDecimal precioPorCantidad = precio.multiply(cantidad);
            ventaTotal = ventaTotal.add(precioPorCantidad);
        }
        setPrecioTotal(ventaTotal);
    }

    //================= RESERVAR STOCK ==================//

    public void reservarStock(int cantidad, String articuloPadreNombre) {
        for (int i = 0; i < cantidad; i++) {
            for (PromocionDetalleEntity detalle : this.getPromocionDetalle()) {
                ArticuloEntity articulo = detalle.getArticulo();
                int cantDetalle = detalle.getCantidad();

                if (articulo instanceof ArticuloManufacturadoEntity manufacturado) {
                    manufacturado.reservarStock(cantDetalle, articuloPadreNombre);
                } else if (articulo instanceof ArticuloInsumoEntity insumo) {
                    insumo.reservarStock((double) cantDetalle, articuloPadreNombre);
                }
            }
        }
    }

    public void liberarStock(int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            for (PromocionDetalleEntity detalle : this.getPromocionDetalle()) {
                ArticuloEntity articulo = detalle.getArticulo();
                int cantDetalle = detalle.getCantidad();

                if (articulo instanceof ArticuloManufacturadoEntity manufacturado) {
                    manufacturado.liberarStock(cantDetalle);
                } else if (articulo instanceof ArticuloInsumoEntity insumo) {
                    insumo.liberarStock(cantDetalle);
                }
            }
        }
    }

    public void confirmarStock(int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            for (PromocionDetalleEntity detalle : this.getPromocionDetalle()) {
                ArticuloEntity articulo = detalle.getArticulo();
                int cantDetalle = detalle.getCantidad();

                if (articulo instanceof ArticuloManufacturadoEntity manufacturado) {
                    manufacturado.confirmarStock(cantDetalle);
                } else if (articulo instanceof ArticuloInsumoEntity insumo) {
                    insumo.confirmarStock(cantDetalle);
                }
            }
        }
    }
}
