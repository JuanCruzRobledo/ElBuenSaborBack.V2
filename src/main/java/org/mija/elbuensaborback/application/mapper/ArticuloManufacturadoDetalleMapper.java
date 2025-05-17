package org.mija.elbuensaborback.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mija.elbuensaborback.application.dto.global.manufacturado.ArticuloManufacturadoDetalleDto;
import org.mija.elbuensaborback.application.dto.request.manufacturado.ArticuloManufacturadoDetalleCreatedRequest;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloManufacturadoDetalleEntity;


@Mapper(componentModel = "spring")
public interface ArticuloManufacturadoDetalleMapper {

    @Mapping(target = "articuloInsumo.id", source = "articuloInsumoId")
    ArticuloManufacturadoDetalleEntity dtoDetalleToEntity(ArticuloManufacturadoDetalleDto detalle);

    @Mapping(target = "articuloInsumoId", source = "articuloInsumo.id")
    @Mapping(target = "articuloDenominacion", source = "articuloInsumo.denominacion")
    ArticuloManufacturadoDetalleDto entityToDto(ArticuloManufacturadoDetalleEntity detalle);

    @Mapping(target = "articuloInsumo.id", source="articuloInsumoId")
    ArticuloManufacturadoDetalleEntity dtoCreateToEntity(ArticuloManufacturadoDetalleCreatedRequest detalle);

}
