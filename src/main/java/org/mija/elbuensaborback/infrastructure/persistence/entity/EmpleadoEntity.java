package org.mija.elbuensaborback.infrastructure.persistence.entity;


import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Entity(name = "empleado")
public class EmpleadoEntity extends PersonaEntity {

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private SucursalEntity sucursal;

}
