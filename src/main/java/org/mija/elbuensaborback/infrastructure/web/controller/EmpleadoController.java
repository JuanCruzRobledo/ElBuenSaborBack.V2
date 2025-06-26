package org.mija.elbuensaborback.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.request.cliente.ClienteCompleteUpdateRequest;
import org.mija.elbuensaborback.application.dto.request.empleado.EmpleadoCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.empleado.EmpleadoUpdateRequest;
import org.mija.elbuensaborback.application.service.EmpleadoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoServiceImpl empleadoService;

    @PostMapping
    public ResponseEntity<?> crearEmpleado(@RequestBody EmpleadoCreatedRequest empleadoCreatedRequest) {
        return ResponseEntity.ok(empleadoService.crearEmpleado(empleadoCreatedRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerEmpleadoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(empleadoService.buscarEmpleadoPorId(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> obtenerEmpleadoPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(empleadoService.buscarEmpleadoPorEmail(email));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> obtenerTodosLosEmpleados() {
        return ResponseEntity.ok(empleadoService.listarEmpleados());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEmpleado(@PathVariable Long id) {
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.ok("Empleado eliminado");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarClienteCompleto(@PathVariable Long id, @RequestBody EmpleadoUpdateRequest clienteBasicUpdateRequest) {
        return ResponseEntity.ok(empleadoService.actualizarEmpleado(id, clienteBasicUpdateRequest));
    }
}
