package org.mija.elbuensaborback.application.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mija.elbuensaborback.application.dto.request.Pedido.DetallePedidoDto;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloInsumoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.DetallePedidoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.ArticuloInsumoRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.ArticuloManufacturadoRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.ArticuloRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class DetallePedidoMapper {

    @Autowired
    protected ArticuloRepositoryImpl articuloRepository;

    @Mapping(target = "articulo", ignore = true)
    public abstract DetallePedidoEntity toEntity(DetallePedidoDto detallePedidoDto);

    @AfterMapping
    protected void mapArticulo(@MappingTarget DetallePedidoEntity entity, DetallePedidoDto dto) {

        Long articuloId = dto.articuloId();

        List<ArticuloEntity> articulos = articuloRepository.findAll();

        for (ArticuloEntity art : articulos) {
            System.out.println("ID: " + art.getId() + ", tipo real: " + art.getClass().getSimpleName());
        }
        ArticuloEntity articulo = articuloRepository.findById(articuloId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un artículo con id: " + articuloId));

        entity.setArticulo(articulo);
    }
}
