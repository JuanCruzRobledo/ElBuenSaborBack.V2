package org.mija.elbuensaborback.domain.model;

import org.mija.elbuensaborback.domain.enums.TipoPromocionEnum;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Promocion {
    private Long id;
    private String denominacion;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private LocalTime horaDesde;
    private LocalTime horaHasta;
    private String descripcionDescuento;
    private Double precioPromocional;
    private TipoPromocionEnum tipoPromocionEnum;
    private Articulo articulo; //No se si tiene que tener cualquier objeto que herede de articulo
    private List<Imagen> listaImagenes;
}
