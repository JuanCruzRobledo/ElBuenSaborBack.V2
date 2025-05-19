package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mija.elbuensaborback.domain.enums.PermissionEnum;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
@Entity(name = "permisos")
public class PermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private PermissionEnum permissionEnum;
}
