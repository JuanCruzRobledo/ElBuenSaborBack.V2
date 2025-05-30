package org.mija.elbuensaborback.application.service.contratos;

import org.mija.elbuensaborback.application.dto.request.insumo.ArticuloActualizarStockPrecioRequest;
import org.mija.elbuensaborback.application.dto.request.insumo.ArticuloInsumoCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.insumo.ArticuloInsumoUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ArticuloInsumoResponse;

import java.util.Set;

public interface ArticuloInsumoService {
    ArticuloInsumoResponse crearArticuloInsumo(ArticuloInsumoCreatedRequest articuloCreatedRequest);
    ArticuloInsumoResponse actualizarArticuloInsumo(Long id , ArticuloInsumoUpdateRequest articuloUpdateRequest);
    ArticuloInsumoResponse obtenerArticuloInsumo(Long id);
    void eliminarArticuloInsumo(Long id);
    Set<ArticuloInsumoResponse> listarArticulosInsumo();
    ArticuloInsumoResponse actualizarPrecioYStock(Long id, ArticuloActualizarStockPrecioRequest articuloUpdateRequest);
}
