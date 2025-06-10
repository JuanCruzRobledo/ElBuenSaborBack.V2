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
        messagingTemplate.convertAndSend("/topic/estado-pedido", estadoPedidoDTO);
    }
    public void notificarPedidoNuevo(PedidoResponse pedidoResponse) {
        messagingTemplate.convertAndSend("/topic/pedido-nuevo", pedidoResponse);
    }
}
