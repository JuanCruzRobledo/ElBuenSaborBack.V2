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
@Entity(name = "localidad")
public class LocalidadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "provincia_id" , referencedColumnName = "id", nullable = false)
    private ProvinciaEntity provincia;

    @Override
    public String toString() {
        return "LocalidadEntity{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", provincia=" + provincia +
                '}';
    }
}
