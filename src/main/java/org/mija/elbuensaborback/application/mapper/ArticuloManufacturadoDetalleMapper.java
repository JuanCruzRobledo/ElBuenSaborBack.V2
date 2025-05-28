package org.mija.elbuensaborback.application.mapper;

import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mija.elbuensaborback.application.dto.global.manufacturado.ArticuloManufacturadoDetalleDto;
import org.mija.elbuensaborback.application.dto.request.Pedido.DetallePedidoDto;
import org.mija.elbuensaborback.application.dto.request.manufacturado.ArticuloManufacturadoDetalleCreatedRequest;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloInsumoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloManufacturadoDetalleEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.DetallePedidoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.ArticuloInsumoRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Mapper(componentModel = "spring")
public abstract class ArticuloManufacturadoDetalleMapper {

    @Autowired
    protected ArticuloInsumoRepositoryImpl articuloInsumoRepository;

    @Mapping(target = "articuloInsumo.id", source = "articuloInsumoId")
    public abstract ArticuloManufacturadoDetalleEntity dtoDetalleToEntity(ArticuloManufacturadoDetalleDto detalle);

    @Mapping(target = "articuloInsumoId", source = "articuloInsumo.id")
    @Mapping(target = "articuloDenominacion", source = "articuloInsumo.denominacion")
    public abstract ArticuloManufacturadoDetalleDto entityToDto(ArticuloManufacturadoDetalleEntity detalle);

    @Mapping(target = "articuloInsumo.id", source="articuloInsumoId")
    public abstract ArticuloManufacturadoDetalleEntity dtoCreateToEntity(ArticuloManufacturadoDetalleCreatedRequest detalle);

    @AfterMapping
    protected void mapArticulo(@MappingTarget ArticuloManufacturadoDetalleEntity entity, ArticuloManufacturadoDetalleCreatedRequest detalle) {
         entity.setArticuloInsumo(articuloInsumoRepository.findById(detalle.articuloInsumoId()).orElseThrow(()-> new EntityNotFoundException("No se encotro el Articulo con el id "+ detalle.articuloInsumoId())));
    }

}
