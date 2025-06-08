package org.mija.elbuensaborback.infrastructure.web.controller;

import org.mija.elbuensaborback.application.dto.request.Pedido.EstadoPedidoDto;
import org.mija.elbuensaborback.application.dto.request.Pedido.PedidoCreatedRequest;
import org.mija.elbuensaborback.application.dto.response.PedidoResponse;
import org.mija.elbuensaborback.application.service.PedidoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoServiceImpl pedidoService;

    public PedidoController(PedidoServiceImpl pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> crearPedido(@RequestBody PedidoCreatedRequest request) {
        PedidoResponse response = pedidoService.crearPedido(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> obtenerPedido(@PathVariable Long id) {
        PedidoResponse response = pedidoService.obtenerPedido(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Set<PedidoResponse>> listarPedido() {
        Set<PedidoResponse> pedidos = pedidoService.listarPedido();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<Set<PedidoResponse>> listarPedidoCliente(@PathVariable Long id) {
        Set<PedidoResponse> pedidos = pedidoService.listarPedidoCliente(id);
        return ResponseEntity.ok(pedidos);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Void> actualizarEstado(
            @PathVariable Long id,
            @RequestBody EstadoPedidoDto dto) {

        pedidoService.cambiarEstadoPedido(id, dto.nuevoEstado());
        return ResponseEntity.ok().build();
    }
}