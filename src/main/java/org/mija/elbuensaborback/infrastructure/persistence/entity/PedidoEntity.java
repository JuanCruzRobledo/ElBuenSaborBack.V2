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
@Entity(name = "pedido")
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime horaEstimadaFinalizacion;
    private BigDecimal total;
    private BigDecimal gastosEnvio;

    @Enumerated(EnumType.STRING)
    private EstadoEnum estadoEnum;
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

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;


    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private SucursalEntity sucursal;

    //ID DE SUCURSAL EN INFRAESTRUCTURE
    //METODO GENERATE FACTURA QUE GENERE Y HAGA UN SET A LA FACTURA
}
