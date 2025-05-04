package org.mija.elbuensaborback.domain.model;


import org.mija.elbuensaborback.infrastructure.persistence.entity.CategoriaEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.SucursalEntity;

import java.math.BigDecimal;
import java.util.Set;




public abstract class Articulo {
    private Long id;
    private String denominacion;
    private BigDecimal precioVenta;
    private Boolean productoActivo;
    private Set<ImagenArticulo> listaImagenes;
    private CategoriaEntity categoriaEntity;
    private SucursalEntity sucursalEntity;
}
