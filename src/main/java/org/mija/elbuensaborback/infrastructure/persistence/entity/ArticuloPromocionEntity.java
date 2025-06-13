package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity(name = "articulo_promocion")
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

}
