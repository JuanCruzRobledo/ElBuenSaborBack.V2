package org.mija.elbuensaborback;

import org.mija.elbuensaborback.infrastructure.persistence.data.*;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ComprobanteCounter;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ComprobanteCounterJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ElBuenSaborBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElBuenSaborBackApplication.class, args);
    }

    /*
    @Bean
    CommandLineRunner init(DataInitializationService dataService, ComprobanteCounterJpaRepository repo) {

        return args -> {
            dataService.init();
            if (!repo.existsById("FACTURA")) {
                repo.save(new ComprobanteCounter("FACTURA", 0L));
            }
        };

    }*/


}
