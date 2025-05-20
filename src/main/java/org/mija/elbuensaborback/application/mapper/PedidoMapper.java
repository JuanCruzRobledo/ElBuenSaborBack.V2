package org.mija.elbuensaborback.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mija.elbuensaborback.application.dto.request.Pedido.PedidoCreatedRequest;
import org.mija.elbuensaborback.application.dto.response.PedidoResponse;
import org.mija.elbuensaborback.infrastructure.persistence.entity.PedidoEntity;

@Mapper(componentModel = "spring", uses = {DetallePedidoMapper.class})
public interface PedidoMapper {

    @Mapping(target = "cliente.id", source = "clienteId")
    @Mapping(target = "domicilio.id", source = "domicilioId")
    PedidoEntity toEntity(PedidoCreatedRequest pedidoCreatedRequest);

    @Mapping( target = "clienteId", source = "cliente.id")
    @Mapping(target = "domicilioId", source = "domicilio.id")
    PedidoResponse toResponse(PedidoEntity pedidoEntity);
}
