package org.mija.elbuensaborback;

import org.mija.elbuensaborback.domain.repository.EmpresaRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.Empresa;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ElBuenSaborBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElBuenSaborBackApplication.class, args);
    }
    @Bean
    CommandLineRunner init(EmpresaRepositoryPort empresaRepository) {
        return args -> {
            Empresa empresa1 = Empresa.builder()
                    .cuil(232323232)
                    .nombre("PanchosSupremos")
                    .razonSocial("PanchosSupremos")
                    .build();

            empresaRepository.save(empresa1);
        };
    }
}
