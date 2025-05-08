package org.mija.elbuensaborback.application.service;

import org.mija.elbuensaborback.application.dto.request.ArticuloManufacturadoCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.ArticuloManufacturadoUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoResponse;
import org.mija.elbuensaborback.domain.repository.ArticuloManufacturadoRepositoryPort;
import org.mija.elbuensaborback.application.mapper.ArticuloManufacturadoMapper;
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
    public ArticuloManufacturadoResponse crearArticulo(ArticuloManufacturadoCreatedRequest articulo) {
        ArticuloManufacturadoEntity articuloEntity = articuloManufacturadoRepository.save(articuloManufacturadoMapper.DtoCreatedToEntity(articulo));

        return articuloManufacturadoMapper.toResponse(articuloEntity);
    }

    @Override
    public ArticuloManufacturadoResponse actualizarArticulo(Long id, ArticuloManufacturadoUpdateRequest articulo) {
        ArticuloManufacturadoEntity articuloEntity = articuloManufacturadoMapper.DtoUpdateToEntity(articulo);
        articuloEntity.setId(id);
        return articuloManufacturadoMapper.toResponse(articuloManufacturadoRepository.save(articuloEntity));
    }


    @Override
    public ArticuloManufacturadoResponse obtenerArticulo(Long id) {
        return articuloManufacturadoMapper.toResponse(articuloManufacturadoRepository.findById(id).orElse(null));
    }

    @Override
    public void eliminarArticulo(Long id) {
        articuloManufacturadoRepository.deleteById(id);
    }
}
