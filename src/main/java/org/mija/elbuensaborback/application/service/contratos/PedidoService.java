package org.mija.elbuensaborback.application.service.contratos;

import org.mija.elbuensaborback.application.dto.request.Pedido.PedidoCreatedRequest;
import org.mija.elbuensaborback.application.dto.response.PedidoResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Set;

public interface PedidoService {
    PedidoResponse crearPedido(PedidoCreatedRequest pedidoCreatedRequest);
    PedidoResponse obtenerPedido(Long id);
    void eliminarPedido(Long id);
    Set<PedidoResponse> listarPedido();
    Set<PedidoResponse> listarPedidoCliente(Long id);
    Set<PedidoResponse> listarPedidoEntreFechas( LocalDate fechaInicio, LocalDate fechaFin);
}
