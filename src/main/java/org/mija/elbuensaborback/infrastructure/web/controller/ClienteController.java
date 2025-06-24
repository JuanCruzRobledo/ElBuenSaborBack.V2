package org.mija.elbuensaborback.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.request.cliente.ClienteUpdateRequest;
import org.mija.elbuensaborback.application.service.ClienteServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/fotoCloud/{id}")
    public ResponseEntity<?> subirFotoCliente(@PathVariable Long id, @RequestParam("archivo") MultipartFile archivo) {
        return ResponseEntity.ok(clienteService.subirFoto(id, archivo));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> obtenerTodosLosClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @DeleteMapping("/id")
    public ResponseEntity<?> eliminarCliente(Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.ok("Empleado eliminado");
    }
}
