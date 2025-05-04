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
    private List<DomicilioEntity> domicilioEntity;

    @OneToMany(mappedBy = "cliente")
    private List<PedidoEntity> listaPedidoEntities;

    @OneToOne
    @JoinColumn(name = "imagen_id")
    private ImagenClienteEntity imagen;
}
