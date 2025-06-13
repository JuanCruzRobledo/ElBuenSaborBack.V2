package org.mija.elbuensaborback.application.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mija.elbuensaborback.application.dto.global.promocion.DetalleDto;
import org.mija.elbuensaborback.application.dto.request.Pedido.DetallePedidoDto;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloPromocionEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.DetallePedidoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.PromocionDetalleEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ArticuloJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public abstract class PromocionDetalleMapper {

    @Autowired
    protected ArticuloJpaRepository articuloRepository;

    @Mapping(target = "articulo", ignore = true)
    public abstract PromocionDetalleEntity toEntity(DetalleDto detalleDto);

    @AfterMapping
    protected void mapArticulo(@MappingTarget PromocionDetalleEntity entity, DetalleDto dto) {

        ArticuloEntity articulo = articuloRepository.findById(dto.articuloId())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un artículo con id: " + dto.articuloId()));

        entity.setArticulo(articulo);
        entity.setCantidad(dto.cantidad());

    }


    @Mapping(target = "articuloId", source = "articulo.id")
    @Mapping(target = "articuloDenominacion", source = "articulo.denominacion")
    public abstract DetalleDto toResponse(PromocionDetalleEntity promocionDetalleEntity);
}
