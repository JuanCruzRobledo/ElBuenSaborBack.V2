package org.mija.elbuensaborback.domain.model;

import java.time.LocalTime;
import java.util.List;


public class Sucursal {
    private Long id;
    private String nombre;
    private LocalTime horarioApertura;
    private LocalTime horarioCierre;
    private Domicilio domicilio;
    private List<Promocion> listaPromociones;
    private List<Categoria> listaCategorias;
    //ID DE EMPRESA EN INFRAESTRUCTURE
}
