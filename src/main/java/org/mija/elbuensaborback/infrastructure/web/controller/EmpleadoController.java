package org.mija.elbuensaborback.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.request.empleado.EmpleadoCreatedRequest;
import org.mija.elbuensaborback.application.service.EmpleadoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empleado")
@RequiredArgsConstructor
public class EmpleadoController {
    private final EmpleadoServiceImpl empleadoService;

    @PostMapping
    public ResponseEntity<?> registrarEmpleado(@RequestBody EmpleadoCreatedRequest empleadoCreatedRequest){
        return ResponseEntity.ok(empleadoService.crearEmpleado(empleadoCreatedRequest));
    }

    @GetMapping("id/{id}")
    public ResponseEntity<?> buscarEmpleadoPorId(@PathVariable Long id){
        return ResponseEntity.ok(empleadoService.buscarEmpleadoPorId(id));
    }

    @GetMapping("email/{email}")
    public ResponseEntity<?> buscarEmpleadoPorEmail(@PathVariable String email){
        return ResponseEntity.ok(empleadoService.buscarEmpleadoPorEmail(email));
    }
}
