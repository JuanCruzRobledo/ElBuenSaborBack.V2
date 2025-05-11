package org.mija.elbuensaborback.application.service.contratos;

import org.mija.elbuensaborback.application.dto.request.manufacturado.ArticuloManufacturadoCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.manufacturado.ArticuloManufacturadoUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoResponse;
import java.util.Set;

public interface ArticuloManufacturadoService {
    // Métodos para gestionar los artículos manufacturados
    ArticuloManufacturadoResponse crearArticulo(ArticuloManufacturadoCreatedRequest articulo);
    ArticuloManufacturadoResponse actualizarArticulo(Long id , ArticuloManufacturadoUpdateRequest articulo);
    ArticuloManufacturadoResponse obtenerArticulo(Long id);
    void eliminarArticulo(Long id);
    Set<ArticuloManufacturadoResponse> listarArticulos();
}