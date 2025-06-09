package org.mija.elbuensaborback.application.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.mija.elbuensaborback.infrastructure.persistence.entity.PedidoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.PedidoRepositoryImpl;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class FacturaService {

    private final PedidoRepositoryImpl pedidoRepository;


    public byte[] generarFacturaPdf(Long idPedido) {
        try {
            // Obtener el pedido
            PedidoEntity pedido = pedidoRepository.findById(idPedido)
                    .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + idPedido));

            // Cargar el archivo JRXML desde los recursos
            InputStream jrxmlStream = getClass().getResourceAsStream("/reportes/factura.jrxml");
            if (jrxmlStream == null) {
                throw new FileNotFoundException("No se encontró el archivo factura.jrxml en /reportes/");
            }

            // Compilar el reporte JRXML a JasperReport
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

            LocalDate fechaPedido = pedido.getFechaPedido();
            Date fechaDate = Date.from(fechaPedido.atStartOfDay(ZoneId.systemDefault()).toInstant());
            // Parámetros del reporte
            Map<String, Object> params = new HashMap<>();
            params.put("nombreCliente", pedido.getCliente().getNombre());
            params.put("numeroFactura", pedido.getFactura().getNumeroComprobante());
            params.put("fecha", fechaDate);
            params.put("total", pedido.getFactura().getTotalVenta());

            // Fuente de datos para el detalle
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(pedido.getListaDetalle());

            // Llenar el reporte con datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

            // Exportar a PDF
            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al generar el PDF de la factura", e);
        }
    }
}
