package org.mija.elbuensaborback.application.service;

import org.mija.elbuensaborback.application.dto.request.ArticuloManufacturadoCreateRequest;
import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoResponse;
import org.mija.elbuensaborback.domain.repository.ArticuloManufacturadoRepositoryPort;
import org.mija.elbuensaborback.infrastructure.mapper.ArticuloManufacturadoMapper;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloManufacturadoEntity;
import org.springframework.stereotype.Service;

@Service
public class ArticuloManufacturadoManagementServiceImpl implements ArticuloManufacturadoService {

    private final ArticuloManufacturadoRepositoryPort articuloManufacturadoRepository;
    private final ArticuloManufacturadoMapper articuloManufacturadoMapper;

    public ArticuloManufacturadoManagementServiceImpl(ArticuloManufacturadoRepositoryPort articuloManufacturadoRepository, ArticuloManufacturadoMapper articuloManufacturadoMapper) {
        this.articuloManufacturadoRepository = articuloManufacturadoRepository;
        this.articuloManufacturadoMapper = articuloManufacturadoMapper;
    }

    @Override
    public ArticuloManufacturadoResponse crearArticulo(ArticuloManufacturadoCreateRequest articulo) {
        ArticuloManufacturadoEntity articuloEntity = articuloManufacturadoRepository.save(articuloManufacturadoMapper.toEntity(articulo));

        return articuloManufacturadoMapper.toResponse(articuloEntity);
    }

    @Override
    public ArticuloManufacturadoResponse actualizarArticulo(ArticuloManufacturadoCreateRequest articulo) {
        return null;
    }

    @Override
    public ArticuloManufacturadoResponse obtenerArticulo(Long id) {
        return null;
    }

    @Override
    public void eliminarArticulo(Long id) {
    }
}
