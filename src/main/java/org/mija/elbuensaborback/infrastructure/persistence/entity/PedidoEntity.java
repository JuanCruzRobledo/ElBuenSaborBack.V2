package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mija.elbuensaborback.domain.enums.EstadoEnum;
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
@Entity
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime horaEstimadaFinalizacion;

    private BigDecimal subTotal;
    private BigDecimal descuento;
    private BigDecimal gastosEnvio;
    private BigDecimal total;
    private BigDecimal totalCosto;

    @Enumerated(EnumType.STRING)
    private EstadoEnum estadoEnum;
    @Enumerated(EnumType.STRING)
    private TipoEnvioEnum tipoEnvioEnum;
    @Enumerated(EnumType.STRING)
    private FormaPagoEnum formaPagoEnum;

    private LocalDate fechaPedido;

    @OneToMany(mappedBy = "pedido")
    private List<DetallePedidoEntity> listaDetalle;

    @OneToOne
    @JoinColumn(name = "factura_id", referencedColumnName = "id")
    private FacturaEntity facturaEntity;

    @ManyToOne
    @JoinColumn(name = "domicilio_id")
    private DomicilioEntity domicilioEntity;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity clienteEntity;


    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private SucursalEntity sucursalEntity;

    //ID DE SUCURSAL EN INFRAESTRUCTURE
    //METODO GENERATE FACTURA QUE GENERE Y HAGA UN SET A LA FACTURA
}
