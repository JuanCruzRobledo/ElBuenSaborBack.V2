package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity(name = "cliente")
public class ClienteEntity extends PersonaEntity {

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "cliente_domicilio",
            joinColumns = @JoinColumn(
                    name = "cliente_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "domicilio_id"
            )
    )
    private List<DomicilioEntity> domicilio;

    @OneToMany(mappedBy = "cliente")
    private List<PedidoEntity> listaPedido;

    @OneToOne
    @JoinColumn(name = "imagen_id")
    private ImagenClienteEntity imagen;
}
