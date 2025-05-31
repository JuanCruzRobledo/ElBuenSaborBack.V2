package org.mija.elbuensaborback.application.service;

import jakarta.persistence.EntityNotFoundException;
import org.mija.elbuensaborback.application.dto.request.Pedido.PedidoCreatedRequest;
import org.mija.elbuensaborback.application.dto.response.PedidoResponse;
import org.mija.elbuensaborback.application.mapper.PedidoMapper;
import org.mija.elbuensaborback.application.service.contratos.PedidoService;
import org.mija.elbuensaborback.domain.enums.EstadoEnum;
import org.mija.elbuensaborback.domain.enums.EstadoPagoEnum;
import org.mija.elbuensaborback.domain.enums.TipoEnvioEnum;
import org.mija.elbuensaborback.infrastructure.persistence.entity.DetallePedidoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.PedidoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.SucursalEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.PedidoRepositoryImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepositoryImpl pedidoRepository;
    private final PedidoMapper pedidoMapper;

    public PedidoServiceImpl(PedidoRepositoryImpl pedidoRepository, PedidoMapper pedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
    }

    @Override
    @Transactional
    public PedidoResponse crearPedido(PedidoCreatedRequest pedidoCreatedRequest) {
        PedidoEntity pedido = pedidoMapper.toEntity(pedidoCreatedRequest);
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
        procesarStock(pedido.getListaDetalle());

        pedido = pedidoRepository.save(pedido);

        pedido = pedidoRepository.findByIdConCliente(pedido.getId())
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));

        return pedidoMapper.toResponse(pedido);
    }

    private void procesarStock(List<DetallePedidoEntity> detalles) {
        for (DetallePedidoEntity detalle : detalles) {
            detalle.getArticulo().descontarStock(detalle.getCantidad());
        }
    }

    @Override
    public PedidoResponse obtenerPedido(Long id) {
        PedidoEntity pedido = pedidoRepository.findById(id).orElseThrow(()->new EntityNotFoundException("No se encontro el pedido con el id "+ id));

        return pedidoMapper.toResponse(pedido);
    }

    @Override
    public void eliminarPedido(Long id) {
        pedidoRepository.deleteById(id);
    }

    @Override
    public Set<PedidoResponse> listarPedido() {
        return pedidoRepository.findAll().stream().map(pedidoMapper::toResponse).collect(Collectors.toSet());
    }

    @Override
    public Set<PedidoResponse> listarPedidoCliente(Long id) {
        return pedidoRepository.findAllByCliente(id).stream().map(pedidoMapper::toResponse).collect(Collectors.toSet());
    }
}
