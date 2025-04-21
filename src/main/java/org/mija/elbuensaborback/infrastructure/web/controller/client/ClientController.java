package org.mija.elbuensaborback.infrastructure.web.controller.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cliente")
public class ClientController {
    @GetMapping
    public String holaSoyCliente(){
        return "Hola soy cliente";
    }
}
