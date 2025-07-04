package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mija.elbuensaborback.domain.enums.FormaPagoEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "factura")
public class FacturaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private FormaPagoEnum formaPagoEnum;
    private BigDecimal totalVenta;
    private LocalDate fechaFacturacion;
    private Long numeroComprobante;

    @OneToOne(cascade = CascadeType.ALL)
    private DatosMercadoPagoEntity datosMercado;

}
