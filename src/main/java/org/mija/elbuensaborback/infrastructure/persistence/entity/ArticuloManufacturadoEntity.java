package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.mija.elbuensaborback.domain.exceptions.StockInsuficienteException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "articulo_manufacturado")
@DiscriminatorValue("ARTICULO_MANUFACTURADO")
public class ArticuloManufacturadoEntity extends ArticuloEntity {

    private String descripcion;

    //private String preparacion; no se si iria

    @OneToMany(mappedBy = "articuloManufacturado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticuloManufacturadoDetalleEntity> articuloManufacturadoDetalle = new ArrayList<>();


    public void tiempoEstimadoCalculado(int tiempoBase){
        Double tiempoDetalle = 0.00;

        for( ArticuloManufacturadoDetalleEntity detalle : articuloManufacturadoDetalle){
            tiempoDetalle += detalle.getArticuloInsumo().getTiempoEstimadoMinutos();
        }
        setTiempoEstimadoMinutos(tiempoBase + tiempoDetalle.intValue());
    }

    public void calcularPrecioCosto() {
        BigDecimal costoTotal = BigDecimal.ZERO;

        for (ArticuloManufacturadoDetalleEntity detalle : articuloManufacturadoDetalle) {
            ArticuloInsumoEntity insumo = detalle.getArticuloInsumo();

            BigDecimal cantidadBase = BigDecimal.valueOf(insumo.convertirACantidadBase(detalle.getCantidad()));
            BigDecimal precioUnitario = insumo.getPrecioVenta(); // ya está por unidad base
            BigDecimal precioPorCantidad = precioUnitario.multiply(cantidadBase);

            costoTotal = costoTotal.add(precioPorCantidad);
        }

        setPrecioCosto(costoTotal);
    }

    public void calcularPrecioVenta(BigDecimal precioVentaManual) {
        if (getPrecioCosto() != null && getMargen() != null) {
            if (getMargen() == 0f) {
                // Si margen es 0, el precio de venta es igual al precio de costo
                setPrecioVenta(getPrecioCosto());
            } else {
                // Aplicar el margen de ganancia
                BigDecimal margen = BigDecimal.valueOf(getMargen()).divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
                BigDecimal nuevoPrecioVenta = getPrecioCosto().multiply(BigDecimal.ONE.add(margen));
                setPrecioVenta(nuevoPrecioVenta);
            }
        } else {
            // Si no hay margen
            setPrecioVenta(precioVentaManual);
        }
    }

    //================= RESERVAR STOCK ==================//

    public void reservarStock(int cantidad, String articuloPadreNombre) {
        for (ArticuloManufacturadoDetalleEntity detalle : this.getArticuloManufacturadoDetalle()) {
            ArticuloInsumoEntity insumo = detalle.getArticuloInsumo();
            double requeridoBase = insumo.convertirACantidadBase(detalle.getCantidad() * cantidad);
            insumo.reservarStock(requeridoBase, articuloPadreNombre);
        }
    }

    public void liberarStock(int cantidad) {
        for (ArticuloManufacturadoDetalleEntity detalle : this.getArticuloManufacturadoDetalle()) {
            ArticuloInsumoEntity insumo = detalle.getArticuloInsumo();
            double requeridoBase = insumo.convertirACantidadBase(detalle.getCantidad() * cantidad);
            insumo.liberarStock(requeridoBase);
        }
    }

    public void confirmarStock(int cantidad) {
        for (ArticuloManufacturadoDetalleEntity detalle : this.getArticuloManufacturadoDetalle()) {
            ArticuloInsumoEntity insumo = detalle.getArticuloInsumo();
            double requeridoBase = insumo.convertirACantidadBase(detalle.getCantidad() * cantidad);
            insumo.confirmarStock(requeridoBase);
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ArticuloManufacturadoEntity {");
        sb.append("\n  id=").append(getId());
        sb.append(",\n  denominacion='").append(getDenominacion()).append('\'');
        sb.append(",\n  descripcion='").append(descripcion).append('\'');
        sb.append(",\n  precioVenta=").append(getPrecioVenta());
        sb.append(",\n  precioCosto=").append(getPrecioCosto());
        sb.append(",\n  tiempoEstimadoMinutos=").append(getTiempoEstimadoMinutos());
        sb.append(",\n  productoActivo=").append(getProductoActivo());

        // Información de categoría sin recursión
        sb.append(",\n  categoria=");
        if (getCategoria().getId() != null) {
            sb.append(getCategoria().getId());
        } else {
            sb.append("null");
        }

        // Información de sucursal sin recursión
        sb.append(",\n  sucursal=");
        if (getSucursal().getId() != null) {
            sb.append(getSucursal().getId());
        } else {
            sb.append("null");
        }

        // Información de detalles sin recursión infinita
        sb.append(",\n  detalles=[");
        if (articuloManufacturadoDetalle != null && !articuloManufacturadoDetalle.isEmpty()) {
            for (int i = 0; i < articuloManufacturadoDetalle.size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append("{ManufacturadoId=");
                sb.append(articuloManufacturadoDetalle.get(i).getArticuloManufacturado().getId());
                ArticuloManufacturadoDetalleEntity detalle = articuloManufacturadoDetalle.get(i);
                sb.append("{insumo=");
                if (detalle.getArticuloInsumo().getId() != null) {
                    sb.append(detalle.getArticuloInsumo().getId());
                } else {
                    sb.append("null");
                }
                sb.append(", cantidad=").append(detalle.getCantidad());
            }
        } else {
            sb.append("vacío");
        }
        sb.append("]");

        // Información de imágenes sin recursión
        sb.append(",\n  imagenes=[");
        if (getImagenesUrls() != null && !getImagenesUrls().isEmpty()) {
            int imageCount = 0;
            for (ImagenArticuloEntity imagen : getImagenesUrls()) {
                if (imageCount > 0) sb.append(", ");
                sb.append("{id=").append(imagen.getId());
                sb.append(", url='").append(imagen.getUrl()).append("'");
                sb.append("}");
                imageCount++;
            }
        } else {
            sb.append("ninguna");
        }
        sb.append("]");

        sb.append("\n}");
        return sb.toString();
    }
}
