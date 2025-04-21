package org.mija.elbuensaborback.infrastructure.web.controller.auth;

import org.mija.elbuensaborback.application.dto.request.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @GetMapping("/hola")
    public String hola(){
        return "hola";
    }

    @PostMapping("/sing-in")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO user){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(user);
    }
    @PostMapping("/sing-up")
    public String registerUser(){
        return "Registro completado";
    }
}
