package org.mija.elbuensaborback.infrastructure.web.controller;

import org.mija.elbuensaborback.application.dto.response.LocalidadResponse;
import org.mija.elbuensaborback.application.dto.response.PaisResponse;
import org.mija.elbuensaborback.application.dto.response.ProvinciaResponse;
import org.mija.elbuensaborback.application.service.UbicacionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping
public class UbicacionController {

    private final UbicacionServiceImpl localidadService;

    public UbicacionController(UbicacionServiceImpl localidadService) {
        this.localidadService = localidadService;
    }


    @GetMapping("/localidades")
    public ResponseEntity<Set<LocalidadResponse>> obtenerLocalidades() {
        return ResponseEntity.ok(localidadService.obtenerLocalidades());
    }

    @GetMapping("/paises")
    public ResponseEntity<Set<PaisResponse>> obtenerPaises() {
        return ResponseEntity.ok(localidadService.obtenerPaises());
    }

    @GetMapping("/provincias")
    public ResponseEntity<Set<ProvinciaResponse>> obtenerProvincias() {
        return ResponseEntity.ok(localidadService.obtenerProvincias());
    }
}
