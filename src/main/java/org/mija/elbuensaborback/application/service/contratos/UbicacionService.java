package org.mija.elbuensaborback.application.service.contratos;

import org.mija.elbuensaborback.application.dto.response.LocalidadResponse;
import org.mija.elbuensaborback.application.dto.response.PaisResponse;
import org.mija.elbuensaborback.application.dto.response.ProvinciaResponse;

import java.util.Set;

public interface UbicacionService {
    Set<LocalidadResponse> obtenerLocalidades();
    Set<PaisResponse> obtenerPaises();
    Set<ProvinciaResponse> obtenerProvincias();
}
