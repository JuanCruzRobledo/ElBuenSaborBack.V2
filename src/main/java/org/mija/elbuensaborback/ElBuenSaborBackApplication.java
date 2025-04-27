package org.mija.elbuensaborback;

import org.mija.elbuensaborback.domain.repository.EmpresaRepositoryPort;
import org.mija.elbuensaborback.domain.repository.LocalidadRepositoryPort;
import org.mija.elbuensaborback.domain.repository.PaisRepositoryPort;
import org.mija.elbuensaborback.domain.repository.SucursalRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalTime;
import java.util.List;

@SpringBootApplication
public class ElBuenSaborBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElBuenSaborBackApplication.class, args);
    }
    @Bean
    CommandLineRunner init(EmpresaRepositoryPort empresaRepository, LocalidadRepositoryPort localidadRepository, SucursalRepositoryPort sucursalRepository) {
        return args -> {
            //CREACION DE LOCALIDAD

            Pais argentina = Pais.builder()
                    .nombre("Argentina")
                    .build();

            Provincia mendoza = Provincia.builder()
                    .nombre("Mendoza")
                    .pais(argentina)
                    .build();

            Localidad godoyCruz = Localidad.builder()
                    .nombre("Godoy Cruz")
                    .provincia(mendoza)
                    .build();

            Localidad lujanDeCuyo = Localidad.builder()
                    .nombre("Luján de Cuyo")
                    .provincia(mendoza)
                    .build();

            Localidad maipu = Localidad.builder()
                    .nombre("Maipú")
                    .provincia(mendoza)
                    .build();

            Localidad lasHeras = Localidad.builder()
                    .nombre("Las Heras")
                    .provincia(mendoza)
                    .build();

            localidadRepository.saveAll(List.of(godoyCruz, maipu, lujanDeCuyo));

            //CREACION DE SUCURSALES
            Sucursal sucursal1 = Sucursal.builder()
                    .nombre("PanchosSupremos Las Heras")
                    .horarioApertura(LocalTime.of(20,0,0))
                    .horarioCierre(LocalTime.of(1,0,0))
                    .domicilio(Domicilio.builder().calle("Av.Pepe").numero(123).codigoPostal("5523").localidad(lasHeras).build())
                    .build();


            //CREACIÓN DE EMPRESA
            Empresa empresa1 = Empresa.builder()
                    .cuil(232323232)
                    .nombre("PanchosSupremos")
                    .razonSocial("PanchosSupremos")
                    .listaSucursal(List.of(sucursal1))
                    .build();

            sucursal1.setEmpresa(empresa1);

            empresaRepository.save(empresa1);
        };
    }
}
