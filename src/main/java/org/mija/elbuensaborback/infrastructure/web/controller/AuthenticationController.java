package org.mija.elbuensaborback.infrastructure.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/auth")
public class AuthenticationController {

    @GetMapping("/login")
    public ResponseEntity<?> login(String username, String password) {
        return ResponseEntity.ok().body("insegurisimo como tu cola");
    }
}
