package org.mija.elbuensaborback.infrastructure.web.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.service.FacturaService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/facturas")
@RequiredArgsConstructor
public class FacturaController {
    private final FacturaService facturaService;

    @GetMapping(value = "/pedido/{idPedido}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> descargarFactura(@PathVariable Long idPedido) {
        byte[] pdfBytes = facturaService.generarFacturaPdf(idPedido);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("factura_pedido_" + idPedido + ".pdf")
                .build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
