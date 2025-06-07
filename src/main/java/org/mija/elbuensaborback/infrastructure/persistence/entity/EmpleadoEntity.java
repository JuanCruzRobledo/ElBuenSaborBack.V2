package org.mija.elbuensaborback.infrastructure.persistence.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@Entity(name = "empleado")
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoEntity extends PersonaEntity {

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private SucursalEntity sucursal;

}
