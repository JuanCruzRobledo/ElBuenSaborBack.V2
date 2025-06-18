package org.mija.elbuensaborback.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.request.cliente.ClienteUpdateRequest;
import org.mija.elbuensaborback.application.service.ClienteServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteServiceImpl clienteService;
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCliente(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.traerCliente(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Long id, @RequestBody ClienteUpdateRequest clienteUpdateRequest) {
        return ResponseEntity.ok(clienteService.actualizarCliente(id, clienteUpdateRequest));
    }

    @PostMapping("/foto/{id}")
    public ResponseEntity<?> subirFotoCliente(@PathVariable Long id, @RequestBody String foto) {
        return ResponseEntity.ok(clienteService.subirFoto(id, foto));
    }
}
