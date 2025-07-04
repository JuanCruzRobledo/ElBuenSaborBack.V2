package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mija.elbuensaborback.domain.enums.AuthProviderEnum;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Entity(name = "usuario")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;

    // Para autenticación OAuth2
    private String oauth2Id;

    @Enumerated(EnumType.STRING)
    private AuthProviderEnum authProviderEnum;

    //usuario Local
    private boolean disabled;
    private boolean accountExpired;
    private boolean accountLocked;
    private boolean credentialsExpired;
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private RoleEntity rol;
}
