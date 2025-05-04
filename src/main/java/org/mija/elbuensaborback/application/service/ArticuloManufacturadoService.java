package org.mija.elbuensaborback.application.service;

import org.mija.elbuensaborback.application.dto.request.ArticuloManufacturadoRequestDto;
import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoResponseDto;
import org.mija.elbuensaborback.domain.model.ArticuloManufacturado;

public interface ArticuloManufacturadoService {
    // Métodos para gestionar los artículos manufacturados
    ArticuloManufacturadoResponseDto crearArticulo(ArticuloManufacturadoRequestDto articulo);
    ArticuloManufacturadoResponseDto actualizarArticulo(ArticuloManufacturadoRequestDto articulo);
    ArticuloManufacturadoResponseDto obtenerArticulo(Long id);
    void eliminarArticulo(Long id);
}