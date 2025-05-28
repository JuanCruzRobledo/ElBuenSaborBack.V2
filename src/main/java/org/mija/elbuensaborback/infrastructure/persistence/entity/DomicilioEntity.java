package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Entity(name = "domicilio")
public class DomicilioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String calle;
    private Integer numero;
    private String codigoPostal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "localidad_id", referencedColumnName = "id", nullable = false)
    private LocalidadEntity localidad;
}
