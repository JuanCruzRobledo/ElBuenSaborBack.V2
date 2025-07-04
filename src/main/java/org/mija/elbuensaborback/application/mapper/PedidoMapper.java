package org.mija.elbuensaborback.application.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mija.elbuensaborback.application.dto.request.Pedido.CheckStockRequest;
import org.mija.elbuensaborback.application.dto.request.Pedido.PedidoCreatedRequest;
import org.mija.elbuensaborback.application.dto.response.PedidoResponse;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ClienteEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.PedidoEntity;

@Mapper(componentModel = "spring", uses = {DetallePedidoMapper.class, DomicilioMapper.class})
public abstract class PedidoMapper {

    @Mapping(target = "cliente.id", ignore = true )
    @Mapping(target = "domicilio.id",  ignore = true )
    public abstract PedidoEntity toEntity(PedidoCreatedRequest pedidoCreatedRequest);

    @AfterMapping
    protected void relacionBidireccional( @MappingTarget PedidoEntity pedido, PedidoCreatedRequest pedidoCreatedRequest ){
        pedido.getListaDetalle().forEach(detalle ->{
            detalle.setPedido(pedido);
        });
    }

    @Mapping( target = "clienteId", source = "cliente.id")
    @Mapping(target = "domicilioId", source = "domicilio.id")
    @Mapping(target = "nombreCompleto", expression = "java(concatenarNombreApellido(pedidoEntity.getCliente()))")
    @Mapping(target = "listaDetalle", source = "listaDetalle") //EXPLICITAMENTE
    @Mapping(target = "domicilio", source = "domicilio")
    public abstract PedidoResponse toResponse(PedidoEntity pedidoEntity);

    protected String concatenarNombreApellido(ClienteEntity cliente) {
        if (cliente == null) return null;
        return cliente.getNombre() + " " + cliente.getApellido();
    }

    public abstract PedidoEntity checkStockToEntity(CheckStockRequest request);
}
