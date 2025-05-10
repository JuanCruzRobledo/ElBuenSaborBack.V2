package org.mija.elbuensaborback.application.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mija.elbuensaborback.application.dto.global.ArticuloManufacturadoDetalleDto;
import org.mija.elbuensaborback.application.dto.request.ArticuloManufacturadoDetalleCreatedRequest;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloInsumoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloManufacturadoDetalleEntity;


@Mapper(componentModel = "spring")
public interface ArticuloManufacturadoDetalleMapper {

    @Mapping(target = "articuloInsumo.id", source = "articuloInsumoId")
    ArticuloManufacturadoDetalleEntity dtoDetalleToEntity(ArticuloManufacturadoDetalleDto detalle);

    @Mapping(target = "articuloInsumoId", source = "articuloInsumo.id")
    ArticuloManufacturadoDetalleDto entityToDto(ArticuloManufacturadoDetalleEntity detalle);

    @Mapping(target = "articuloInsumo.id", source="articuloInsumoId")
    ArticuloManufacturadoDetalleEntity dtoCreateToEntity(ArticuloManufacturadoDetalleCreatedRequest detalle);

}
