package org.mija.elbuensaborback.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.request.Pedido.EstadoPedidoDto;
import org.mija.elbuensaborback.application.dto.response.PedidoResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PedidoWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public void notificarCambioEstado(EstadoPedidoDto estadoPedidoDTO) {
        System.out.println("DEBUG - Notificando ESTADO: " + estadoPedidoDTO.pedidoId());
        messagingTemplate.convertAndSend("/topic/pedido-estado", estadoPedidoDTO);
    }
    public void notificarPagado(PedidoResponse pedidoResponse) {
        System.out.println("DEBUG - Notificando PAGADO: " + pedidoResponse.id());
        messagingTemplate.convertAndSend("/topic/pedido-pagado", pedidoResponse);
    }

    public void notificarPedidoNuevo(PedidoResponse pedidoResponse) {
        System.out.println("DEBUG - Notificando NUEVO: " + pedidoResponse.id());
        messagingTemplate.convertAndSend("/topic/pedido-nuevo", pedidoResponse);
    }
}
