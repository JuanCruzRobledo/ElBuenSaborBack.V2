package org.mija.elbuensaborback.application.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.global.promocion.ArticuloPromocionDto;
import org.mija.elbuensaborback.application.dto.request.promocion.ArticuloPromocionCreatedRequest;
import org.mija.elbuensaborback.application.mapper.ArticuloPromocionMapper;
import org.mija.elbuensaborback.application.service.contratos.ArticuloPromocionService;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloPromocionEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.ArticuloPromocionRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticuloPromocionServiceImpl implements ArticuloPromocionService {

    private final ArticuloPromocionRepositoryImpl articuloPromocionRepository;
    private final ArticuloPromocionMapper articuloPromocionMapper;


    @Override
    public ArticuloPromocionDto obtenerArticuloPromocion(Long id) {

        ArticuloPromocionEntity articuloPromocionEntity = articuloPromocionRepository.findById(id).orElseThrow(()->new EntityNotFoundException("No se pudo encontrar la Promocion"));

        return articuloPromocionMapper.toResponse(articuloPromocionEntity) ;
    }

    @Override
    public List<ArticuloPromocionDto> listarArticulosPromocion() {
        return articuloPromocionRepository.findAll().stream().map(articuloPromocionMapper::toResponse).toList();
    }

    @Override
    public ArticuloPromocionDto crearPromocion(ArticuloPromocionCreatedRequest articuloPromocionCreatedRequest) {
        return null;
    }

    @Override
    public ArticuloPromocionDto actualizarPromocion(Long id ,ArticuloPromocionDto articuloPromocionDto) {
        return null;
    }

    @Override
    public ArticuloPromocionDto eliminarPromocion(Long id) {
        return null;
    }
}
