package org.mija.elbuensaborback.infrastructure.web.controller.admin;

import org.mija.elbuensaborback.application.service.AdminService;
import org.mija.elbuensaborback.infrastructure.persistence.entity.Empresa;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public String holaSoyAdmin(){
        return "Hola Soy Admin";
    }

    @GetMapping("/getAllEmpresas")
    public ResponseEntity<List<Empresa>> getAllEmpresas(){
       return ResponseEntity.status(HttpStatus.OK).body( adminService.findAllEmpresas());
    }
}
