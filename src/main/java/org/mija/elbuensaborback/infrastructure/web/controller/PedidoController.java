package org.mija.elbuensaborback.infrastructure.web.controller;

import org.mija.elbuensaborback.application.dto.request.Pedido.CheckStockRequest;
import org.mija.elbuensaborback.application.dto.request.Pedido.EstadoPedidoDto;
import org.mija.elbuensaborback.application.dto.request.Pedido.PedidoCreatedRequest;
import org.mija.elbuensaborback.application.dto.response.PedidoResponse;
import org.mija.elbuensaborback.application.service.PedidoServiceImpl;
import org.mija.elbuensaborback.domain.exceptions.StockInsuficienteException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
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

    @PostMapping("/verificar-stock")
    public ResponseEntity<?> verificarStock(@RequestBody CheckStockRequest request) {
        pedidoService.verificarStockParaElPedido(request);
        return ResponseEntity.ok(Map.of("message", "Stock suficiente para realizar el pedido."));
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

    @GetMapping("/getAll")
    public ResponseEntity<Set<PedidoResponse>> listarPedido() {
        Set<PedidoResponse> pedidos = pedidoService.listarPedido();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/diario/getAll")
    public ResponseEntity<Set<PedidoResponse>> listarPedidoDiario() {
        Set<PedidoResponse> pedidos = pedidoService.listarPedidoDiario();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/fecha-rango")
    public ResponseEntity<Set<PedidoResponse>> listarPedidosPorFecha(
            @RequestParam(value = "fechaInicio", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(value = "fechaFin", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin
    ) {
        Set<PedidoResponse> pedidos = pedidoService.listarPedidoEntreFechas(fechaInicio, fechaFin);
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

        pedidoService.cambiarEstadoPedido(id, dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/pagar/{id}")
    public ResponseEntity<?> pagarPedido(@PathVariable Long id){
        return ResponseEntity.ok(pedidoService.pagarPedido(id));
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity<?> cancelarPedido(@PathVariable Long id){
        return ResponseEntity.ok(pedidoService.cancelarPedido(id));
    }
}