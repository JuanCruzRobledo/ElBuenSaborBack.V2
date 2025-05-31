package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EstadisticaDiaria {
    @Id
    private LocalDate fecha;

    private BigDecimal ingresoTotal;
    private BigDecimal costoTotal;
    private BigDecimal ganancia;

    // Getters, setters, constructores
}
