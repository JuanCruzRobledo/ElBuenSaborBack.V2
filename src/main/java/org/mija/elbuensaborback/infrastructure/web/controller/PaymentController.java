package org.mija.elbuensaborback.infrastructure.web.controller;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.mija.elbuensaborback.application.dto.request.Pedido.PedidoCreatedRequest;
import org.mija.elbuensaborback.application.dto.response.PreferenceResponseDto;
import org.mija.elbuensaborback.application.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;


@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-preference")
    public ResponseEntity<?> createPreference(@RequestBody PedidoCreatedRequest pedido) {
        try {
            PreferenceResponseDto preferenceResponseDTO = paymentService.crearPedidoYPreferencia(pedido);
            return ResponseEntity.ok(preferenceResponseDTO);
        } catch (MPException | MPApiException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> recibirWebhook(@RequestBody Map<String, Object> body) {
        try {
            System.out.println("Webhook recibido: " + body);
            String type = (String) body.get("type");
            Map<String, Object> data = (Map<String, Object>) body.get("data");

            if (type != null && type.equals("payment") && data != null && data.get("id") != null) {
                Long paymentId = Long.valueOf(data.get("id").toString());
                System.out.println("Procesando pago con ID: " + paymentId);
                paymentService.procesarPago(paymentId);
            } else {
                System.out.println("Webhook recibido sin 'type=payment' o sin 'data.id'");
            }

            return ResponseEntity.ok("Webhook procesado correctamente");

        } catch (Exception e) {
            System.out.println("Error al procesar el webhook");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar el webhook");
        }
    }

    @GetMapping("/rechazar/{id}")
    public RedirectView rechazarPedido(
            @PathVariable Long id,
            @RequestParam Map<String, String> params
    ) {
        System.out.println("Pago rechazado para el pedido ID: " + id);
        System.out.println("Parámetros recibidos: " + params);

        paymentService.rechazarPedido(id);
        return new RedirectView("http://localhost:5173/pago-rechazado");
    }

    @GetMapping("/aprobar/{id}")
    public RedirectView aprobarPedido(
            @PathVariable Long id,
            @RequestParam Map<String, String> params
    ) {
        System.out.println("Pago aprobado para el pedido ID: " + id);
        System.out.println("Parámetros recibidos: " + params);

        return new RedirectView("http://localhost:5173/pago-aprobado/"+id);
    }
}

