package org.mija.elbuensaborback.domain.model;

import java.util.List;

public class Cliente extends Persona{
    private Long id;
    private Usuario usuario;
    private List<Domicilio> domicilio;
    private List<Pedido> listaPedidos;
    private Imagen imagen;
}
