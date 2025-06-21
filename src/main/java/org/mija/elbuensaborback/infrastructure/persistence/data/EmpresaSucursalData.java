package org.mija.elbuensaborback.infrastructure.persistence.data;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.infrastructure.persistence.entity.DomicilioEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.EmpresaEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.LocalidadEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.SucursalEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.EmpresaJpaRepository;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.LocalidadJpaRepository;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.SucursalJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaSucursalData {

    private final LocalidadJpaRepository localidadRepository;
    private final EmpresaJpaRepository empresaRepository;

    public void initEmpresaYSucursal() {
        LocalidadEntity lasHeras = localidadRepository.findByNombre("Las Heras");

        SucursalEntity sucursal = SucursalEntity.builder()
                .nombre("PanchosSupremos Las Heras")
                .horarioApertura(LocalTime.of(20, 0))
                .horarioCierre(LocalTime.of(1, 0))
                .domicilio(DomicilioEntity.builder()
                        .calle("Av.Pepe").numero(123).codigoPostal("5523").activo(true).localidad(lasHeras)
                        .build())
                .build();

        EmpresaEntity empresa = EmpresaEntity.builder()
                .cuil(232323232)
                .nombre("PanchosSupremos")
                .razonSocial("PanchosSupremos")
                .listaSucursal(List.of(sucursal))
                .build();

        sucursal.setEmpresa(empresa);

        empresaRepository.save(empresa);
    }
}
