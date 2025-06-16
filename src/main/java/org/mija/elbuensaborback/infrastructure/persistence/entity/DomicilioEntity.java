package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private String descripcion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "localidad_id", referencedColumnName = "id", nullable = false)
    private LocalidadEntity localidad;

    @Override
    public String toString() {
        return "DomicilioEntity{" +
                "id=" + id +
                ", calle='" + calle + '\'' +
                ", numero=" + numero +
                ", codigoPostal='" + codigoPostal + '\'' +
                ", localidad=" + localidad +
                '}';
    }
}
