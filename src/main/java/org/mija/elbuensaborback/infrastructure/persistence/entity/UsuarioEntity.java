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
    private String username;
    private String email;

    //usuario OAuth2
    private String oauth2Id;
    private AuthProviderEnum authProviderEnum;
    //usuario Local
    private boolean isEnabled;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity rol;

}
