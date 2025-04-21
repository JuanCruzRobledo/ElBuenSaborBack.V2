package org.mija.elbuensaborback.domain.model;

import org.mija.elbuensaborback.domain.enums.AuthProviderEnum;



public class Usuario {
    private Long id;
    private String username;
    private String email;

    //Usuario OAuth2
    private String oauth2Id;
    private AuthProviderEnum authProviderEnum;
    //Usuario Local
    private boolean isEnabled;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private String password;
    private Role rol;

}
