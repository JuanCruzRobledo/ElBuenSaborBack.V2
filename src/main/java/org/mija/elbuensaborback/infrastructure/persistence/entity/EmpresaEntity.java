package org.mija.elbuensaborback.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "empresa")
public class EmpresaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String razonSocial;
    private Integer cuil;
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true ) //defecto fech type lazy
    private List<SucursalEntity> listaSucursal;
}
