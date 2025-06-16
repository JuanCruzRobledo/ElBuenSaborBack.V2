package org.mija.elbuensaborback.application.service.contratos;

import org.mija.elbuensaborback.application.dto.global.promocion.ArticuloPromocionDto;
import org.mija.elbuensaborback.application.dto.request.promocion.ArticuloPromocionCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.promocion.ArticuloPromocionUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ArticuloPromocionMenuBasicResponse;

import java.util.List;

public interface ArticuloPromocionService {

    ArticuloPromocionDto obtenerArticuloPromocion(Long id);
    List<ArticuloPromocionDto> listarArticulosPromocion();
    List<ArticuloPromocionMenuBasicResponse> listarArticulosPromocionMenu();
    ArticuloPromocionDto crearPromocion(ArticuloPromocionCreatedRequest articuloPromocionCreatedRequest);
    ArticuloPromocionDto actualizarPromocion(Long id, ArticuloPromocionUpdateRequest articuloPromocionUpdateRequest);
    ArticuloPromocionDto eliminarPromocion(Long id);
}
