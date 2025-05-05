package org.mija.elbuensaborback.application.service;

import org.mija.elbuensaborback.application.dto.request.ArticuloManufacturadoRequestDto;
import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoResponseDto;
import org.mija.elbuensaborback.application.service.mapper.ArticuloManufacturadoDtoMapper;
import org.mija.elbuensaborback.domain.repository.ArticuloManufacturadoRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class ArticuloManufacturadoManagementService implements ArticuloManufacturadoService {

    private final ArticuloManufacturadoRepositoryPort articuloManufacturadoRepository;
    private final ArticuloManufacturadoDtoMapper articuloManufacturadoDtoMapper;

    public ArticuloManufacturadoManagementService(ArticuloManufacturadoRepositoryPort articuloManufacturadoRepository, ArticuloManufacturadoDtoMapper articuloManufacturadoDtoMapper) {
        this.articuloManufacturadoRepository = articuloManufacturadoRepository;
        this.articuloManufacturadoDtoMapper = articuloManufacturadoDtoMapper;
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
