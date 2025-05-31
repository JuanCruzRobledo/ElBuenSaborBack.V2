package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mija.elbuensaborback.domain.enums.EstadoEnum;
import org.mija.elbuensaborback.domain.enums.EstadoPagoEnum;
import org.mija.elbuensaborback.domain.enums.FormaPagoEnum;
import org.mija.elbuensaborback.domain.enums.TipoEnvioEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Entity(name = "pedido")
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime horaEstimadaFinalizacion;
    private BigDecimal total;
    private BigDecimal costoTotal;
    private BigDecimal gastosEnvio;
    private String indicaciones;

    @Enumerated(EnumType.STRING)
    private EstadoEnum estadoEnum;
    @Enumerated(EnumType.STRING)
    private EstadoPagoEnum estadoPagoEnum;
    @Enumerated(EnumType.STRING)
    private TipoEnvioEnum tipoEnvioEnum;
    @Enumerated(EnumType.STRING)
    private FormaPagoEnum formaPagoEnum;
    private LocalDate fechaPedido;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL , orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DetallePedidoEntity> listaDetalle;

    @OneToOne
    @JoinColumn(name = "factura_id", referencedColumnName = "id")
    private FacturaEntity factura;

    @ManyToOne
    @JoinColumn(name = "domicilio_id")
    private DomicilioEntity domicilio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;


    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private SucursalEntity sucursal;

    //METODO GENERATE FACTURA QUE GENERE Y HAGA UN SET A LA FACTURA

    public void calcularTotalPedido() {
        BigDecimal total = BigDecimal.ZERO;

        for (DetallePedidoEntity detallePedidoEntity : listaDetalle) {
            total = total.add(detallePedidoEntity.getSubTotal());
        }

        if (tipoEnvioEnum.compareTo(TipoEnvioEnum.TAKEAWAY) < 0) {
            total = total.add(getGastosEnvio());
        } else {
            total = total.multiply(BigDecimal.valueOf(0.9));
        }

        this.total = total;
    }

    public void calcularCostoTotalPedido() {
        BigDecimal costo = BigDecimal.ZERO;

        for (DetallePedidoEntity detalle : listaDetalle) {
            ArticuloEntity articulo = detalle.getArticulo();
            BigDecimal costoUnitario;

            if (articulo instanceof ArticuloManufacturadoEntity manufacturado) {
                costoUnitario = manufacturado.getPrecioCosto();
            } else if (articulo instanceof ArticuloInsumoEntity insumo) {
                costoUnitario = insumo.getPrecioCompra();
            } else {
                throw new IllegalStateException("Tipo de artículo no soportado para cálculo de costo.");
            }

            if (costoUnitario == null) {
                throw new RuntimeException("El artículo '" + articulo.getDenominacion() + "' no tiene precio de costo definido.");
            }

            BigDecimal cantidad = BigDecimal.valueOf(detalle.getCantidad());
            BigDecimal costoParcial = costoUnitario.multiply(cantidad);

            costo = costo.add(costoParcial);
        }

        this.costoTotal = costo;
    }

}
