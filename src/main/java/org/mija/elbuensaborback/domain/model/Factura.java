package org.mija.elbuensaborback.domain.model;

import org.mija.elbuensaborback.domain.enums.FormaPagoEnum;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Factura {
    private Long id;
    private LocalDate fechaFacturacion;
    private Integer mpPaymentId;
    private Integer mpMerchantOrderId;
    private String mpPreferenceId;
    private String mpPaymentType;
    private FormaPagoEnum formaPagoEnum;
    private BigDecimal totalVenta;
    //private BigDecimal gastosEnvio; //No se si deberia ir
    //private BigDecimal descuento; //No se si deberia ir

    //No tiene mucho sentido
    //private FacturaVentaDetalle facturaVentaDetalle;
    //DATOS DE MERCADO PAGO
}
