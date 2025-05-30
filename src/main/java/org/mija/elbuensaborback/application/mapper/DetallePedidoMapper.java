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

import java.math.BigDecimal;


@Mapper(componentModel = "spring")
public abstract class DetallePedidoMapper {

    @Autowired
    protected ArticuloRepositoryImpl articuloRepository;

    @Mapping(target = "articulo", ignore = true)
    public abstract DetallePedidoEntity toEntity(DetallePedidoDto detallePedidoDto);

    @AfterMapping
    protected void mapArticulo(@MappingTarget DetallePedidoEntity entity, DetallePedidoDto dto) {

        Long articuloId = dto.articuloId();
        Integer cantidad = dto.cantidad();

        ArticuloEntity articulo = articuloRepository.findById(articuloId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un artículo con id: " + articuloId));

        entity.setArticulo(articulo);

        double subTotal = cantidad * articuloId;

        entity.setSubTotal(new BigDecimal(subTotal));
    }

    @Mapping(target = "articuloId" , source = "articulo.id")
    @Mapping(target = "articuloDenominacion" , source = "articulo.denominacion")
    public abstract DetallePedidoDto toDto(DetallePedidoEntity detallePedidoEntity);
}
