package org.mija.elbuensaborback.domain.model;


import java.util.List;

public class Empleado extends Persona {
    private Long id;
    private Usuario usuario;
    private List<Pedido> listaPedido;
}
