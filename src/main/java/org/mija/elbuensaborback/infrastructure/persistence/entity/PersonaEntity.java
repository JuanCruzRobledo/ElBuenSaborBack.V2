package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity(name = "persona")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_persona") // opcional
public abstract class PersonaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String telefono;
    private String email;

    @OneToOne()
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private UsuarioEntity usuario;

}