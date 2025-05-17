package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "articulo_manufacturado")
public class ArticuloManufacturadoEntity extends ArticuloEntity {

    private String descripcion;
    private BigDecimal precioCosto;

    //private String preparacion; no se si iria

    @OneToMany(mappedBy = "articuloManufacturado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticuloManufacturadoDetalleEntity> articuloManufacturadoDetalle = new ArrayList<>();;

    public boolean sePuedePreparar(){
        return null != articuloManufacturadoDetalle;
    }

    public BigDecimal precioCostoCalculado(){
        return null == precioCosto ? BigDecimal.ZERO : precioCosto;
    }

    public void addDetalle(ArticuloManufacturadoDetalleEntity detalle) {
        if (this.articuloManufacturadoDetalle == null) {
            this.articuloManufacturadoDetalle = new ArrayList<>();
        }
        this.articuloManufacturadoDetalle.add(detalle);
        detalle.setArticuloManufacturado(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ArticuloManufacturadoEntity {");
        sb.append("\n  id=").append(getId());
        sb.append(",\n  denominacion='").append(getDenominacion()).append('\'');
        sb.append(",\n  descripcion='").append(descripcion).append('\'');
        sb.append(",\n  precioVenta=").append(getPrecioVenta());
        sb.append(",\n  precioCosto=").append(precioCosto);
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
