package org.mija.elbuensaborback.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.request.cliente.ClienteBasicUpdateRequest;
import org.mija.elbuensaborback.application.dto.request.cliente.ClienteCompleteUpdateRequest;
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

    @PutMapping("/basic/{id}")
    public ResponseEntity<?> actualizarBasicCliente(@PathVariable Long id, @RequestBody ClienteBasicUpdateRequest clienteBasicUpdateRequest) {
        return ResponseEntity.ok(clienteService.actualizarCliente(id, clienteBasicUpdateRequest));
    }

    @PutMapping("/complete/{id}")
    public ResponseEntity<?> actualizarClienteCompleto(@PathVariable Long id, @RequestBody ClienteCompleteUpdateRequest clienteBasicUpdateRequest) {
        return ResponseEntity.ok(clienteService.actualizarClienteCompleto(id, clienteBasicUpdateRequest));
    }

    @PostMapping("/foto/{id}")
    public ResponseEntity<?> subirFotoCliente(@PathVariable Long id, @RequestParam("archivo") MultipartFile archivo) {
        return ResponseEntity.ok(clienteService.subirFoto(id, archivo));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> obtenerTodosLosClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
