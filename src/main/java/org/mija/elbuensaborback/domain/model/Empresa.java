package org.mija.elbuensaborback.domain.model;

import java.util.List;
/**
 Clase que representa a la Empresa que es dueña de muchas sucursales.
 Contiene nombre, razónSocial, cuil y sus correspondientes Sucrusales.

 @author Juan Cruz Robledo
 @version 1.0
 */

public class Empresa {
    private Long id;
    private String nombre;
    private String razonSocial;
    private Integer cuil;
    private List<Sucursal> listaSucursal;
}
