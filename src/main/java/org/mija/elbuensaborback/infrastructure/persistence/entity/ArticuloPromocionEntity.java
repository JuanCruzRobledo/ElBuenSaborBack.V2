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
    private String descripcionDescuento;
    private BigDecimal precioPromocional;

    @OneToMany(mappedBy = "articuloPromocion", cascade = CascadeType.ALL)
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
    public void calcularPrecioPromocional(){
        setPrecioPromocional(this.getPrecioVenta().multiply(BigDecimal.valueOf(0.9)));
    }

    public void calcularPrecioVenta(){
        BigDecimal ventaTotal = BigDecimal.ZERO;

        for (PromocionDetalleEntity detalle : this.getPromocionDetalle()) {
            BigDecimal precio = detalle.getArticulo().getPrecioVenta();
            BigDecimal cantidad = BigDecimal.valueOf(detalle.getCantidad());
            BigDecimal precioPorCantidad = precio.multiply(cantidad);
            ventaTotal = ventaTotal.add(precioPorCantidad);
        }
        setPrecioVenta(ventaTotal);
    }
}
