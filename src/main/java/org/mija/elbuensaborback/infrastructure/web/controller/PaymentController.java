package org.mija.elbuensaborback.infrastructure.web.controller;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.response.PreferenceResponseDto;
import org.mija.elbuensaborback.application.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;


@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @Value("${url.front.client}")
    private String urlFront;

    @PostMapping("/create-preference")
    public ResponseEntity<?> createPreference(@RequestBody Long pedidoId) {
        PreferenceResponseDto preferenceResponseDTO = paymentService.crearPreferencia(pedidoId);
        return ResponseEntity.ok(preferenceResponseDTO);
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> recibirWebhook(@RequestBody Map<String, Object> body) {
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
    }

    @GetMapping("/rechazar/{id}")
    public RedirectView rechazarPedido(@PathVariable Long id, @RequestParam Map<String, String> params) {
        paymentService.rechazarPedido(id);

        StringBuilder redirectUrl = new StringBuilder(urlFront + "/cart?");
        params.forEach((key, value) -> redirectUrl.append(key).append("=").append(value).append("&"));
        redirectUrl.setLength(redirectUrl.length() - 1);

        return new RedirectView(redirectUrl.toString());
    }

    @GetMapping("/aprobar/{id}")
    public RedirectView aprobarPedido(@PathVariable Long id, @RequestParam Map<String, String> params) {
        System.out.println("Pago aprobado para el pedido ID: " + id);
        System.out.println("Parámetros recibidos: " + params);

        StringBuilder redirectUrl = new StringBuilder(urlFront + "/cart?");
        params.forEach((key, value) -> redirectUrl.append(key).append("=").append(value).append("&"));
        redirectUrl.setLength(redirectUrl.length() - 1);

        return new RedirectView(redirectUrl.toString());
    }

    @GetMapping("/pendiente/{id}")
    public RedirectView pedidoPendiente(@PathVariable Long id, @RequestParam Map<String, String> params) {
        System.out.println("Pago pendiente para el pedido ID: " + id);
        System.out.println("Parámetros recibidos: " + params);

        StringBuilder redirectUrl = new StringBuilder(urlFront + "/cart?");
        params.forEach((key, value) -> redirectUrl.append(key).append("=").append(value).append("&"));
        redirectUrl.setLength(redirectUrl.length() - 1);

        return new RedirectView(redirectUrl.toString());
    }
}
