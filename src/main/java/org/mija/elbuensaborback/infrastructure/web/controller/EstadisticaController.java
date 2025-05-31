package org.mija.elbuensaborback.infrastructure.web.controller;

import org.mija.elbuensaborback.application.service.EstadisticaService;
import org.mija.elbuensaborback.infrastructure.persistence.entity.EstadisticaDiaria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/estadisticas")
public class EstadisticaController {

    private final EstadisticaService estadisticaService;

    @Autowired
    public EstadisticaController(EstadisticaService estadisticaService) {
        this.estadisticaService = estadisticaService;
    }

    // Endpoint temporal solo para pruebas
    @PostMapping("/generar")
    public ResponseEntity<String> generarEstadisticas() {
        estadisticaService.generarEstadisticasDelDiaActual();
        return ResponseEntity.ok("Estadísticas generadas correctamente.");
    }

    @GetMapping
    public ResponseEntity<List<EstadisticaDiaria>> obtenerEstadisticasPorRango(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        List<EstadisticaDiaria> estadisticas = estadisticaService.obtenerEstadisticasPorRango(fechaInicio, fechaFin);
        return ResponseEntity.ok(estadisticas);
    }
}