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


    public void descontarStock(int cantidad) {
        for (int i = 0; i < cantidad ; i++) {
            for (PromocionDetalleEntity detalle : this.getPromocionDetalle()) {
                if (detalle.getArticulo() instanceof ArticuloManufacturadoEntity) {
                    ((ArticuloManufacturadoEntity) detalle.getArticulo()).descontarStock(detalle.getCantidad());
                } else if (detalle.getArticulo() instanceof ArticuloInsumoEntity) {
                    ((ArticuloInsumoEntity) detalle.getArticulo()).descontarStock(detalle.getCantidad().doubleValue());
                }
            }
        }
    }

    public void tiempoEstimadoCalculado(int tiempoBase){
        Integer max = 0;
        for (PromocionDetalleEntity detalle : this.getPromocionDetalle()) {
            if (detalle.getArticulo().getTiempoEstimadoMinutos() > max) {
                max = detalle.getArticulo().getTiempoEstimadoMinutos();
            }
        }
        this.setTiempoEstimadoMinutos(max + tiempoBase);
    }

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
    public void calcularPrecioVenta(){
        setPrecioVenta(this.getPrecioTotal().multiply(BigDecimal.valueOf(0.9)));
    }

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
}
