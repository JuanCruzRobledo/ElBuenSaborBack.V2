package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@AllArgsConstructor
@SuperBuilder
@Entity(name = "cliente")
public class ClienteEntity extends PersonaEntity {

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
    private List<DomicilioEntity> domicilio;

    @OneToMany(mappedBy = "cliente")
    private List<PedidoEntity> listaPedido;

    @OneToOne
    @JoinColumn(name = "imagen_id")
    private ImagenClienteEntity imagen;
}
