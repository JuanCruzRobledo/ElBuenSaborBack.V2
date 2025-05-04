package org.mija.elbuensaborback.infrastructure.persistence.entity;


import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Entity
public class EmpleadoEntity extends PersonaEntity {

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private SucursalEntity sucursalEntity;

}
