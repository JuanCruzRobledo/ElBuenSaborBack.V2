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
public class Cliente extends Persona {


    @ManyToMany
    @JoinTable(
            name = "cliente_domicilio",
            joinColumns = @JoinColumn(
                    name = "cliente_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "domicilio_id"
            )
    )
    private List<Domicilio> domicilio;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> listaPedidos;

    @OneToOne
    @JoinColumn(name = "imagen_id")
    private Imagen imagen;
}
