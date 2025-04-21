package org.mija.elbuensaborback.domain.model;

import org.mija.elbuensaborback.domain.enums.EstadoEnum;
import org.mija.elbuensaborback.domain.enums.FormaPagoEnum;
import org.mija.elbuensaborback.domain.enums.TipoEnvioEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Pedido {
    private Long id;
    private LocalTime horaEstimadaFinalizacion;
    // SE CAMBIO POR ALGUN MOTIVO?
    private BigDecimal subTotal; //<-PARA MI DEBERIA IR PARA SABER EL TOTAL SIN EL DESCUENTO NI GASTO DE ENVIO
    private BigDecimal descuento;
    private BigDecimal gastosEnvio;
    private BigDecimal total;
    private BigDecimal totalCosto;
    private EstadoEnum estadoEnum;
    private TipoEnvioEnum tipoEnvioEnum;
    private FormaPagoEnum formaPagoEnum;
    private LocalDate fechaPedido;
    private List<DetallePedido> listaDetalle;
    private Factura factura;
    private Domicilio domicilio;
    //ID DE SUCURSAL EN INFRAESTRUCTURE
    //METODO GENERATE FACTURA QUE GENERE Y HAGA UN SET A LA FACTURA
}
