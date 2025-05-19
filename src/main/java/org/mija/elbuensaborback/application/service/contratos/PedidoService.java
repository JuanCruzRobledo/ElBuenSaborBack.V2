package org.mija.elbuensaborback.application.service.contratos;

import org.mija.elbuensaborback.application.dto.request.Pedido.PedidoCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.Pedido.PedidoUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.PedidoResponse;

import java.util.Set;

public interface PedidoService {
    PedidoResponse crearPedido(PedidoCreatedRequest categoriaDto);
    PedidoResponse actualizarPedido(Long id , PedidoUpdateRequest categoriaDto);
    PedidoResponse obtenerPedido(Long id);
    void eliminarPedido(Long id);
    Set<PedidoResponse> listarPedido();
}
