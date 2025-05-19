package org.mija.elbuensaborback.infrastructure.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @GetMapping("/hola")
    public String clienteHola() {
        return "cliente asegurado";
    }

    @GetMapping("/chau")
    public String clienteChau() {
        return "cliente asegurado";
    }
}
