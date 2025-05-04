package org.mija.elbuensaborback.application.service;

import org.mija.elbuensaborback.application.dto.request.ArticuloManufacturadoRequestDto;
import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoResponseDto;
import org.mija.elbuensaborback.domain.repository.ArticuloManufacturadoRepositoryPort;
import org.mija.elbuensaborback.domain.service.ArticuloManufacturadoOrchestratorService;
import org.springframework.stereotype.Service;

@Service
public class ArticuloManufacturadoManagementService implements ArticuloManufacturadoService {

    private final ArticuloManufacturadoOrchestratorService articuloManufacturadoOrchestratorService;
    private final ArticuloManufacturadoRepositoryPort articuloManufacturadoRepository;

    public ArticuloManufacturadoManagementService(ArticuloManufacturadoOrchestratorService articuloManufacturadoOrchestratorService, ArticuloManufacturadoRepositoryPort articuloManufacturadoRepository) {
        this.articuloManufacturadoOrchestratorService = articuloManufacturadoOrchestratorService;
        this.articuloManufacturadoRepository = articuloManufacturadoRepository;
    }


    @Override
    public ArticuloManufacturadoResponseDto crearArticulo(ArticuloManufacturadoRequestDto articulo) {
        return null;
    }

    @Override
    public ArticuloManufacturadoResponseDto actualizarArticulo(ArticuloManufacturadoRequestDto articulo) {
        return null;
    }

    @Override
    public ArticuloManufacturadoResponseDto obtenerArticulo(Long id) {
        return null;
    }

    @Override
    public void eliminarArticulo(Long id) {

    }
}
