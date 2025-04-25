package org.mija.elbuensaborback.infrastructure.persistence.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Entity
public class Empleado extends Persona {

    @ManyToOne
    @JoinColumn(name = "sucursal_id")
    private Sucursal sucursal;

}
