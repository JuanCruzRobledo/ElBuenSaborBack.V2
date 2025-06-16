package org.mija.elbuensaborback.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.response.LocalidadResponse;
import org.mija.elbuensaborback.application.dto.response.PaisResponse;
import org.mija.elbuensaborback.application.dto.response.ProvinciaResponse;
import org.mija.elbuensaborback.application.service.UbicacionServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UbicacionController {

    private final UbicacionServiceImpl localidadService;

    @GetMapping("/localidades/{id}")
    public ResponseEntity<Set<LocalidadResponse>> obtenerLocalidades(@PathVariable Long id) {
        return ResponseEntity.ok(localidadService.obtenerLocalidades(id));
    }

    @GetMapping("/paises")
    public ResponseEntity<Set<PaisResponse>> obtenerPaises() {
        return ResponseEntity.ok(localidadService.obtenerPaises());
    }

    @GetMapping("/provincias/{id}")
    public ResponseEntity<Set<ProvinciaResponse>> obtenerProvincias(@PathVariable Long id) {
        return ResponseEntity.ok(localidadService.obtenerProvincias(id));
    }
}
