package org.mija.elbuensaborback.infrastructure.persistence.data;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.infrastructure.persistence.entity.LocalidadEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.PaisEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ProvinciaEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.LocalidadJpaRepository;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.PaisJpaRepository;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ProvinciaJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UbicacionesData {
    private final PaisJpaRepository paisRepository;
    private final ProvinciaJpaRepository provinciaRepository;
    private final LocalidadJpaRepository localidadRepository;

    public void initPaisesProvinciasLocalidades() {
        // Paises
        PaisEntity argentina = paisRepository.save(PaisEntity.builder().nombre("Argentina").build());
        PaisEntity chile = paisRepository.save(PaisEntity.builder().nombre("Chile").build());

        // Provincias
        ProvinciaEntity mendoza = provinciaRepository.save(
                ProvinciaEntity.builder().nombre("Mendoza").pais(argentina).build()
        );

        // Localidades
        LocalidadEntity godoyCruz = LocalidadEntity.builder().nombre("Godoy Cruz").provincia(mendoza).build();
        LocalidadEntity lujanDeCuyo = LocalidadEntity.builder().nombre("Luján de Cuyo").provincia(mendoza).build();
        LocalidadEntity maipu = LocalidadEntity.builder().nombre("Maipú").provincia(mendoza).build();
        LocalidadEntity lasHeras = LocalidadEntity.builder().nombre("Las Heras").provincia(mendoza).build();

        localidadRepository.saveAll(List.of(godoyCruz, lujanDeCuyo, maipu, lasHeras));
    }
}
