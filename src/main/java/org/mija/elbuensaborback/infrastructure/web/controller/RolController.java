package org.mija.elbuensaborback.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.service.RolServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rol")
@RequiredArgsConstructor
public class RolController {

    private final RolServiceImpl rolService;

    @GetMapping("/getAll")
    public ResponseEntity<?> obtenerTodosLosRoles(){
        return ResponseEntity.ok(rolService.listarBasicRoles());
    }
}
