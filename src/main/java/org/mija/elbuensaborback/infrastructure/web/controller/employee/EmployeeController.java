package org.mija.elbuensaborback.infrastructure.web.controller.employee;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/empleado")
public class EmployeeController {

    @GetMapping
    public String holaSoyEmpleado(){
        return "Hola Soy empleado";
    }
}
