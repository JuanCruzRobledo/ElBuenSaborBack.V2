package org.mija.elbuensaborback.application.service;


import com.mercadopago.MercadoPagoConfig;
import org.mija.elbuensaborback.application.dto.response.PreferenceResponseDto;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.PedidoRepositoryImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*
@Service
public class PaymentService {
    private final PedidoRepositoryImpl pedidoRepository;
    //private final InstrumentoRepository instrumentoRepository;

    @Value("${mercadopago.access.token}")
    private String mercadoPagoAccessToken;

    @Value("${url.ngrok}")
    private String urlNgrok;

    public PaymentService(PedidoRepositoryImpl pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }


    public PreferenceResponseDTO crearPedidoYPreferencia(PedidoDTO pedidoRequest) throws MPException, MPApiException {
        // Inicializar SDK
        MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);

        // Crear entidad Pedido
        Pedido newPedido = Pedido.builder()
                .fechaPedido(LocalDate.now())
                .pedidoDetalle(new ArrayList<>())
                .estadoPedido(EstadoPedido.PENDIENTE)
                .build();

        Set<Long> instrumentosContados = new HashSet<>();
        double costoEnvios = 0.0;

        for (PedidoDetalleRequestDTO detalle : pedidoRequest.pedidoDetalle()) {
            Instrumento instrumento = instrumentoRepository.findById(detalle.instrumentoId())
                    .orElseThrow(() -> new RuntimeException("Instrumento no encontrado"));

            // Agregar detalle
            PedidoDetalle newDetalle = PedidoDetalle.builder()
                    .instrumento(instrumento)
                    .cantidad(detalle.cantidad())
                    .pedido(newPedido)
                    .build();
            newPedido.getPedidoDetalle().add(newDetalle);

            // Calcular costo de envío solo una vez por instrumento
            if (instrumentosContados.add(instrumento.getId())) {
                String costoEnvioStr = instrumento.getCostoEnvio();
                if (!"G".equalsIgnoreCase(costoEnvioStr)) {
                    try {
                        costoEnvios += Double.parseDouble(costoEnvioStr);
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Costo de envío inválido para instrumento ID " + instrumento.getId() + ": " + costoEnvioStr);
                    }
                }
            }
        }

        newPedido.calcularTotal(costoEnvios);

        // Guardar Pedido en BD
        Pedido pedidoGuardado = pedidoRepository.save(newPedido);

        // Crear preferencia de Mercado Pago
        PreferenceClient preferenceClient = new PreferenceClient();

        // Crear lista de ítems de instrumentos
        List<PreferenceItemRequest> items = pedidoGuardado.getPedidoDetalle().stream()
                .map(detalle -> PreferenceItemRequest.builder()
                        .title(detalle.getInstrumento().getInstrumento())
                        .quantity(detalle.getCantidad())
                        .unitPrice(BigDecimal.valueOf(detalle.getInstrumento().getPrecio()))
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

        return new PreferenceResponseDTO(preference.getId(), pedidoGuardado.getId(), pedidoGuardado.getTotalPedido());
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

            Pedido pedido = pedidoRepository.findById(pedidoId)
                    .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + pedidoId));

            if ("approved".equals(status)) {
                pedido.setEstadoPedido(EstadoPedido.APROBADO);
            } else if ("rejected".equals(status)) {
                pedido.setEstadoPedido(EstadoPedido.RECHAZADO);
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
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        pedido.setEstadoPedido(EstadoPedido.RECHAZADO);
        pedidoRepository.save(pedido);
    }


}
 */