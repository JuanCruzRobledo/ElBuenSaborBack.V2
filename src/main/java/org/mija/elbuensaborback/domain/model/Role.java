package org.mija.elbuensaborback.domain.model;

import org.mija.elbuensaborback.domain.enums.RolEnum;

import java.util.Set;

public class Role {
    private Long id;
    private RolEnum denominacion;
    private Set<Permission> permisos;
}
