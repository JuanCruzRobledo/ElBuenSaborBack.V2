package org.mija.elbuensaborback.application.service;


import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import org.mija.elbuensaborback.application.dto.request.Pedido.DetallePedidoDto;
import org.mija.elbuensaborback.application.dto.request.Pedido.PedidoCreatedRequest;
import org.mija.elbuensaborback.application.dto.response.PreferenceResponseDto;
import org.mija.elbuensaborback.domain.enums.EstadoEnum;
import org.mija.elbuensaborback.domain.enums.EstadoPagoEnum;
import org.mija.elbuensaborback.domain.enums.FormaPagoEnum;
import org.mija.elbuensaborback.domain.enums.TipoEnvioEnum;
import org.mija.elbuensaborback.infrastructure.persistence.entity.*;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.ArticuloRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.PedidoRepositoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Service
public class PaymentService {
    private final PedidoRepositoryImpl pedidoRepository;
    private final ArticuloRepositoryImpl articuloRepository;

    @Value("${mercadopago.access.token}")
    private String mercadoPagoAccessToken;

    @Value("${url.ngrok}")
    private String urlNgrok;

    public PaymentService(PedidoRepositoryImpl pedidoRepository, ArticuloRepositoryImpl articuloRepository) {
        this.pedidoRepository = pedidoRepository;
        this.articuloRepository = articuloRepository;
    }


    public PreferenceResponseDto crearPedidoYPreferencia(PedidoCreatedRequest pedidoRequest) throws MPException, MPApiException {
        // Inicializar SDK
        MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);

        // Crear entidad Pedido
        PedidoEntity newPedido = PedidoEntity.builder()
                .fechaPedido(LocalDate.now())
                .horaEstimadaFinalizacion(LocalTime.MIN) //calcular
                //.total() calcular despues
                .gastosEnvio(new BigDecimal(22))
                .estadoEnum(EstadoEnum.PENDIENTE)
                .estadoPagoEnum(EstadoPagoEnum.PENDIENTE)
                .tipoEnvioEnum(TipoEnvioEnum.valueOf(pedidoRequest.tipoEnvioEnum().name()))
                .formaPagoEnum(FormaPagoEnum.valueOf(pedidoRequest.formaPagoEnum().name()))
                .domicilio(DomicilioEntity.builder().id(pedidoRequest.domicilioId()).build())
                .cliente(ClienteEntity.builder().id(pedidoRequest.clienteId()).build())
                .sucursal(SucursalEntity.builder().id(1L).build())
                .listaDetalle(new ArrayList<>())
                .build();


        double costoEnvios = 0.0;

        for (DetallePedidoDto detalle : pedidoRequest.listaDetalle()) {
            ArticuloEntity articulo = articuloRepository.findById(detalle.id())
                    .orElseThrow(() -> new RuntimeException("Articulo no encontrado"));

            // Agregar detalle
            DetallePedidoEntity newDetalle = DetallePedidoEntity.builder()
                    .cantidad(detalle.cantidad())
                    .subTotal(detalle.subTotal())
                    .articulo(articulo)
                    .pedido(newPedido)
                    .build();
            newPedido.getListaDetalle().add(newDetalle);

        }

        //newPedido.calcularTotal(costoEnvios);

        // Guardar Pedido en BD
        PedidoEntity pedidoGuardado = pedidoRepository.save(newPedido);

        // Crear preferencia de Mercado Pago
        PreferenceClient preferenceClient = new PreferenceClient();

        // Crear lista de ítems de instrumentos
        List<PreferenceItemRequest> items = pedidoGuardado.getListaDetalle().stream()
                .map(detalle -> PreferenceItemRequest.builder()
                        .title(detalle.getArticulo().getDenominacion())
                        .quantity(detalle.getCantidad())
                        .unitPrice(detalle.getArticulo().getPrecioVenta())
                        .build())
                .toList();

        // Si hay costo de envío, agregarlo como ítem adicional
        List<PreferenceItemRequest> itemsConEnvio = new ArrayList<>(items);
        if (costoEnvios > 0) {
            PreferenceItemRequest envioItem = PreferenceItemRequest.builder()
                    .title("Costo de Envío")
                    .quantity(1)
                    .unitPrice(BigDecimal.valueOf(costoEnvios))
                    .build();
            itemsConEnvio.add(envioItem);
        }

        PreferenceBackUrlsRequest preferenceBackUrls = PreferenceBackUrlsRequest.builder()
                .failure(urlNgrok+"/payment/rechazar/" + String.valueOf(pedidoGuardado.getId()))
                .pending("https://google.com")
                .success(urlNgrok+"/payment/aprobar/" + String.valueOf(pedidoGuardado.getId()))
                .build();

        PreferencePayerRequest payer = PreferencePayerRequest.builder()
                .name("Jorgito")
                .email("jorgito@example.com")
                .build();

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .payer(payer)
                .notificationUrl(urlNgrok+"/payment/webhook")
                .items(itemsConEnvio) // Usamos la lista con envío incluido si aplica
                .backUrls(preferenceBackUrls)
                .autoReturn("approved")
                .dateOfExpiration(OffsetDateTime.now(ZoneOffset.UTC).plusMinutes(10)) //Borrar si no anda
                .externalReference(String.valueOf(pedidoGuardado.getId()))
                .build();

        Preference preference = preferenceClient.create(preferenceRequest);

        return new PreferenceResponseDto(preference.getId(), pedidoGuardado.getId(), pedidoGuardado.getTotal());
    }


    public void procesarPago(Long paymentId) {
        try {
            System.out.println("🔄 Iniciando proceso de verificación del pago: " + paymentId);

            MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);

            PaymentClient client = new PaymentClient();
            Payment payment = client.get(paymentId);

            String status = payment.getStatus();
            String externalReference = payment.getExternalReference();

            System.out.println("Estado del pago: " + status);
            System.out.println("Referencia externa: " + externalReference);

            if (externalReference == null) return;

            Long pedidoId = Long.parseLong(externalReference);

            PedidoEntity pedido = pedidoRepository.findById(pedidoId)
                    .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + pedidoId));

            if ("approved".equals(status)) {
                pedido.setEstadoPagoEnum(EstadoPagoEnum.PAGADO);
            } else if ("rejected".equals(status)) {
                pedido.setEstadoPagoEnum(EstadoPagoEnum.RECHAZADO);
            }

            pedidoRepository.save(pedido);
            System.out.println("Pedido actualizado y guardado");

        } catch (MPApiException e) {
            System.out.println("Error de la API de Mercado Pago:");
            System.out.println("Respuesta: " + e.getApiResponse().getContent());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error general al procesar el pago:");
            e.printStackTrace();
        }
    }

    public void rechazarPedido(Long id) {
        PedidoEntity pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        pedido.setEstadoPagoEnum(EstadoPagoEnum.RECHAZADO);
        pedidoRepository.save(pedido);
    }


}
