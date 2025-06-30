package org.mija.elbuensaborback.application.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.request.Pedido.CheckStockRequest;
import org.mija.elbuensaborback.application.dto.request.Pedido.EstadoPedidoDto;
import org.mija.elbuensaborback.application.dto.request.Pedido.PedidoCreatedRequest;
import org.mija.elbuensaborback.application.dto.response.PedidoResponse;
import org.mija.elbuensaborback.application.mapper.PedidoMapper;
import org.mija.elbuensaborback.application.service.contratos.PedidoService;
import org.mija.elbuensaborback.domain.enums.EstadoEnum;
import org.mija.elbuensaborback.domain.enums.EstadoPagoEnum;
import org.mija.elbuensaborback.domain.enums.TipoEnvioEnum;
import org.mija.elbuensaborback.infrastructure.persistence.entity.*;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.ClienteRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.DomicilioRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.LocalidadRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.PedidoRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.web.controller.PedidoWebSocketController;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepositoryImpl pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final PedidoWebSocketController webSocketController;
    private final ComprobanteService comprobanteService;
    private final ClienteRepositoryImpl clienteRepository;
    private final DomicilioRepositoryImpl domicilioRepository;
    private final LocalidadRepositoryImpl localidadRepository;
    private final StockVerifierService stockVerifierService;

    @Override
    @Transactional
    public PedidoResponse crearPedido(PedidoCreatedRequest pedidoCreatedRequest) {
        ClienteEntity cliente = clienteRepository.findById(pedidoCreatedRequest.clienteId()).orElseThrow(()-> new EntityNotFoundException("No se pudo encontrar el cliente"));
        DomicilioEntity domicilio = domicilioRepository.findById(pedidoCreatedRequest.domicilioId()).orElseThrow(()-> new EntityNotFoundException("No se pudo encontrar el domicilio"));

        //Se calcula el subtotal de cada detalle en el mapper de detalle
        PedidoEntity pedido = pedidoMapper.toEntity(pedidoCreatedRequest);
        pedido.setCliente(cliente);
        pedido.setDomicilio(domicilio);
        pedido.setHoraEstimadaFinalizacion(LocalTime.MIN);
        pedido.setFechaPedido(LocalDate.now());
        pedido.setEstadoEnum(EstadoEnum.PENDIENTE);
        pedido.setEstadoPagoEnum(EstadoPagoEnum.PENDIENTE);
        pedido.setSucursal(SucursalEntity.builder().id(1L).build());

        if (pedidoCreatedRequest.tipoEnvioEnum().compareTo(TipoEnvioEnum.TAKEAWAY) == 0 ){
            pedido.setGastosEnvio(new BigDecimal(0));
        } else {
            pedido.setGastosEnvio(new BigDecimal(1999));
        }

        Integer max = 0;
        for (DetallePedidoEntity detalle : pedido.getListaDetalle()){
            if (detalle.getArticulo().getTiempoEstimadoMinutos() > max){
                max = detalle.getArticulo().getTiempoEstimadoMinutos();
            }
        }
        pedido.setHoraEstimadaFinalizacion(LocalTime.now().plusMinutes(max));

        pedido.calcularTotalPedido();
        pedido.calcularCostoTotalPedido();

        // Procesar stock
        pedido.reservarStock();
        pedido.setDomicilioSnapshot(domicilio.toString());

        pedido = pedidoRepository.save(pedido);

        pedido = pedidoRepository.findByIdConCliente(pedido.getId())
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));

        PedidoResponse respuesta = pedidoMapper.toResponse(pedido);
        webSocketController.notificarPedidoNuevo(respuesta);

        return respuesta;
    }


    @Override
    public PedidoResponse obtenerPedido(Long id) {
        PedidoEntity pedido = pedidoRepository.findById(id).orElseThrow(()->new EntityNotFoundException("No se encontro el pedido con el id "+ id));
        pedido.setDomicilio(DomicilioEntity.fromSnapshotString(pedido.getDomicilioSnapshot()));
        LocalidadEntity localidad = localidadRepository.findByNombre(pedido.getDomicilio().getLocalidad().getNombre());
        pedido.getDomicilio().setLocalidad(localidad);
        return pedidoMapper.toResponse(pedido);
    }

    @Override
    public void eliminarPedido(Long id) {
        pedidoRepository.deleteById(id);
    }

    @Override
    public Set<PedidoResponse> listarPedido() {
        return pedidoRepository.findAll().stream().map(pedido -> {
            pedido.setDomicilio(DomicilioEntity.fromSnapshotString(pedido.getDomicilioSnapshot()));
            LocalidadEntity localidad = localidadRepository.findByNombre(pedido.getDomicilio().getLocalidad().getNombre());
            pedido.getDomicilio().setLocalidad(localidad);
            return pedidoMapper.toResponse(pedido);
        }).collect(Collectors.toSet());
    }

    @Override
    public Set<PedidoResponse> listarPedidoDiario() {
        return pedidoRepository.findAllDiarios().stream().map(pedido -> {
            pedido.setDomicilio(DomicilioEntity.fromSnapshotString(pedido.getDomicilioSnapshot()));
            LocalidadEntity localidad = localidadRepository.findByNombre(pedido.getDomicilio().getLocalidad().getNombre());
            pedido.getDomicilio().setLocalidad(localidad);
            return pedidoMapper.toResponse(pedido);
        }).collect(Collectors.toSet());
    }

    @Override
    public Set<PedidoResponse> listarPedidoCliente(Long id) {
        return pedidoRepository.findAllByCliente(id).stream().map(pedido -> {
            pedido.setDomicilio(DomicilioEntity.fromSnapshotString(pedido.getDomicilioSnapshot()));
            LocalidadEntity localidad = localidadRepository.findByNombre(pedido.getDomicilio().getLocalidad().getNombre());
            pedido.getDomicilio().setLocalidad(localidad);
            return pedidoMapper.toResponse(pedido);
        }).collect(Collectors.toSet());
    }

    @Override
    public Set<PedidoResponse> listarPedidoEntreFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio != null && fechaFin != null) {
            List<PedidoEntity> pedidos = pedidoRepository.obtenerPedidosPorRango(fechaInicio, fechaFin);
            return pedidos.stream().map(pedidoMapper::toResponse).collect(Collectors.toSet());
        } else if (fechaInicio != null) {
            List<PedidoEntity> pedidos = pedidoRepository.obtenerPedidosDesdeFecha(fechaInicio);
            return pedidos.stream().map(pedidoMapper::toResponse).collect(Collectors.toSet());
        } else if (fechaFin != null) {
            List<PedidoEntity> pedidos = pedidoRepository.obtenerPedidosHastaFecha(fechaFin);
            return pedidos.stream().map(pedidoMapper::toResponse).collect(Collectors.toSet());
        } else {
            return pedidoRepository.findAll().stream().map(pedidoMapper::toResponse).collect(Collectors.toSet());
        }
    }

    @Transactional
    public void cambiarEstadoPedido(Long pedidoId, EstadoPedidoDto actualizacionPedido) {
        PedidoEntity pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        if (actualizacionPedido.nuevoEstado() != null){
            if (actualizacionPedido.nuevoEstado() == EstadoEnum.CANCELADO) {
                new RuntimeException("No se puede cambiar a este estado");
            }
            if (actualizacionPedido.nuevoEstado() == EstadoEnum.PREPARACION){
                pedido.confirmarStock();
            }
            pedido.setEstadoEnum(actualizacionPedido.nuevoEstado());
        }
        /*
        if (actualizacionPedido.estadoPagoEnum() != null){
            pedido.setEstadoPagoEnum(actualizacionPedido.estadoPagoEnum());
        }*/

        if (actualizacionPedido.horaEstimadaFinalizacion() != null){
            pedido.setHoraEstimadaFinalizacion(actualizacionPedido.horaEstimadaFinalizacion());
        }
        if (actualizacionPedido.tipoEnvioEnum() != null){
            pedido.setTipoEnvioEnum(actualizacionPedido.tipoEnvioEnum());
        }


        pedido = pedidoRepository.save(pedido);

        EstadoPedidoDto dto = new EstadoPedidoDto(
                pedido.getId(),
                pedido.getEstadoEnum(),
                //pedido.getEstadoPagoEnum(),
                pedido.getHoraEstimadaFinalizacion(),
                pedido.getTipoEnvioEnum()
        );

        webSocketController.notificarCambioEstado(dto);
    }

    @Transactional
    public PedidoResponse pagarPedido(Long id){
        PedidoEntity pedido = pedidoRepository.findById(id).orElseThrow(()->new EntityNotFoundException("No se encontro el pedido con el id "+ id));

        pedido.setEstadoPagoEnum(EstadoPagoEnum.PAGADO);

        Long nro = comprobanteService.generarNumeroComprobante("FACTURA");
        pedido.generarFactura(nro,null);

        pedido = pedidoRepository.save(pedido);

        PedidoResponse respuesta = pedidoMapper.toResponse(pedido);

        webSocketController.notificarPagado(respuesta);

        return respuesta;
    }

    @Transactional
    public PedidoResponse cancelarPedido(Long id){
        PedidoEntity pedido = pedidoRepository.findById(id).orElseThrow(()->new EntityNotFoundException("No se encontro el pedido con el id "+ id));

        // Liberar Stock solo si esta en pendiente
        if (pedido.getEstadoEnum() == EstadoEnum.PENDIENTE) {
            pedido.liberarStock();
        }

        if (pedido.getEstadoEnum() == EstadoEnum.ENTREGADO || pedido.getEstadoEnum() == EstadoEnum.TERMINADO) {
            throw new IllegalStateException("No se puede cancelar un pedido ya entregado o terminado.");
        }

        pedido.setEstadoPagoEnum(EstadoPagoEnum.RECHAZADO);
        pedido.setEstadoEnum(EstadoEnum.CANCELADO);


        pedido = pedidoRepository.save(pedido);

        PedidoResponse respuesta = pedidoMapper.toResponse(pedido);

        EstadoPedidoDto dto = new EstadoPedidoDto(
                pedido.getId(),
                pedido.getEstadoEnum(),
                //pedido.getEstadoPagoEnum(),
                pedido.getHoraEstimadaFinalizacion(),
                pedido.getTipoEnvioEnum()
        );

        webSocketController.notificarCambioEstado(dto);

        return respuesta;
    }


    public void verificarStockParaElPedido(CheckStockRequest pedidoCreatedRequest) {
        PedidoEntity pedido = pedidoMapper.checkStockToEntity(pedidoCreatedRequest);
        stockVerifierService.verificarStock(pedido);
    }

}
