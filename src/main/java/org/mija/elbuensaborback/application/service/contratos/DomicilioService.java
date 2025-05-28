package org.mija.elbuensaborback.application.service.contratos;

import org.mija.elbuensaborback.application.dto.global.categoria.CategoriaDto;
import org.mija.elbuensaborback.application.dto.request.domicilio.DomicilioCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.domicilio.DomicilioUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.CategoriaWithSubcategoriasResponse;
import org.mija.elbuensaborback.application.dto.response.DomicilioResponse;
import org.mija.elbuensaborback.application.dto.response.LocalidadResponse;

import java.util.Set;

public interface DomicilioService {

    DomicilioResponse crearDomicilio(Long idCliente, DomicilioCreatedRequest domicilioCreatedRequest);
    DomicilioResponse actualizarDomicilio(Long idDomicilio, DomicilioUpdateRequest request);
    DomicilioResponse obtenerDomicilio(Long idDomicilio);
    void eliminarDomicilio(Long idDomicilio);
    Set<DomicilioResponse> listarDomicilios(Long idCliente);
}
