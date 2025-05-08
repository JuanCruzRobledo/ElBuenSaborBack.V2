package org.mija.elbuensaborback.application.service;

import org.mija.elbuensaborback.application.dto.request.ArticuloManufacturadoCreateRequest;
import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoResponse;

public interface ArticuloManufacturadoService {
    // Métodos para gestionar los artículos manufacturados
    ArticuloManufacturadoResponse crearArticulo(ArticuloManufacturadoCreateRequest articulo);
    ArticuloManufacturadoResponse actualizarArticulo(ArticuloManufacturadoCreateRequest articulo);
    ArticuloManufacturadoResponse obtenerArticulo(Long id);
    void eliminarArticulo(Long id);
}