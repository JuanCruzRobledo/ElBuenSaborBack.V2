package org.mija.elbuensaborback.infrastructure.web.controller;

import org.mija.elbuensaborback.application.dto.request.domicilio.DomicilioCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.domicilio.DomicilioUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.DomicilioResponse;
import org.mija.elbuensaborback.application.service.DomicilioServiceImpl;
import org.mija.elbuensaborback.application.service.contratos.DomicilioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/cliente/{idCliente}/domicilios")
public class DomicilioController {
    private final DomicilioServiceImpl domicilioService;

    public DomicilioController(DomicilioServiceImpl domicilioService) {
        this.domicilioService = domicilioService;
    }


    @PostMapping
    public ResponseEntity<DomicilioResponse> crearDomicilio(@PathVariable Long idCliente, @RequestBody DomicilioCreatedRequest domicilioCreatedRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(domicilioService.crearDomicilio(idCliente,domicilioCreatedRequest));
    }

    @GetMapping
    public ResponseEntity<Set<DomicilioResponse>> listarDomicilios(@PathVariable Long idCliente) {
        return ResponseEntity.ok(domicilioService.listarDomicilios(idCliente));
    }

    @PutMapping("/{idDomicilio}")
    public ResponseEntity<DomicilioResponse> actualizarDomicilio(
            @PathVariable Long idDomicilio,
            @RequestBody DomicilioUpdateRequest request) {
        return ResponseEntity.ok(domicilioService.actualizarDomicilio(idDomicilio, request));
    }

    @DeleteMapping("/{idDomicilio}")
    public ResponseEntity<Void> eliminarDomicilio(@PathVariable Long idDomicilio) {
        domicilioService.eliminarDomicilio(idDomicilio);
        return ResponseEntity.noContent().build();
    }
}