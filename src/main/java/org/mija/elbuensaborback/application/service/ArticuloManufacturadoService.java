package org.mija.elbuensaborback.application.service;

import org.mija.elbuensaborback.application.dto.request.ArticuloManufacturadoCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.ArticuloManufacturadoUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoResponse;

public interface ArticuloManufacturadoService {
    // Métodos para gestionar los artículos manufacturados
    ArticuloManufacturadoResponse crearArticulo(ArticuloManufacturadoCreatedRequest articulo);
    ArticuloManufacturadoResponse actualizarArticulo(Long id , ArticuloManufacturadoUpdateRequest articulo);
    ArticuloManufacturadoResponse obtenerArticulo(Long id);
    void eliminarArticulo(Long id);
}