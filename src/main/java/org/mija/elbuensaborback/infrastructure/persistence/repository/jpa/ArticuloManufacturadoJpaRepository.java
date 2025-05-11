package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoBasicResponse;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloManufacturadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticuloManufacturadoJpaRepository extends JpaRepository<ArticuloManufacturadoEntity, Long> {

    //@Query("SELECT * FROM ")
    //CAMBIAR POR ArticuloManufacturadoBasicResponse
    //List<ArticuloManufacturadoBasicResponse> findAllBasic();
}
