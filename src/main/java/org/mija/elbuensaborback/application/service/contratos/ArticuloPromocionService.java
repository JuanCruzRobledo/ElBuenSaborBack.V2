package org.mija.elbuensaborback.application.service.contratos;

import org.mija.elbuensaborback.application.dto.global.promocion.ArticuloPromocionDto;
import org.mija.elbuensaborback.application.dto.request.promocion.ArticuloPromocionCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.promocion.ArticuloPromocionUpdateRequest;

import java.util.List;

public interface ArticuloPromocionService {

    ArticuloPromocionDto obtenerArticuloPromocion(Long id);
    List<ArticuloPromocionDto> listarArticulosPromocion();
    ArticuloPromocionDto crearPromocion(ArticuloPromocionCreatedRequest articuloPromocionCreatedRequest);
    ArticuloPromocionDto actualizarPromocion(Long id, ArticuloPromocionUpdateRequest articuloPromocionUpdateRequest);
    ArticuloPromocionDto eliminarPromocion(Long id);
}
