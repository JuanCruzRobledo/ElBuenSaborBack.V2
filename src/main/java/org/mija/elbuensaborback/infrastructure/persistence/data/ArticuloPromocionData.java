package org.mija.elbuensaborback.infrastructure.persistence.data;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.infrastructure.persistence.entity.*;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ArticuloJpaRepository;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ArticuloPromocionJpaRepository;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.CategoriaJpaRepository;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.SucursalJpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticuloPromocionData {

    private final ArticuloPromocionJpaRepository articuloPromocionRepository;
    private final ArticuloJpaRepository articuloRepository;
    private final SucursalJpaRepository sucursalRepository;
    private final CategoriaJpaRepository categoriaRepository;

    public void initPromociones() {
        CategoriaEntity comboIndividual = categoriaRepository.findByDenominacion("Combo Individual");
        CategoriaEntity comboFamiliar = categoriaRepository.findByDenominacion("Combo Familiar");

        SucursalEntity sucursal = sucursalRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la sucursal"));

        ArticuloEntity cocaCola = articuloRepository.findByDenominacion("Coca Cola 500ml")
                .orElseThrow(() -> new RuntimeException("No se encontró Coca Cola 500ml"));

        ArticuloEntity bbq = articuloRepository.findByDenominacion("BBQ")
                .orElseThrow(() -> new RuntimeException("No se encontró BBQ"));

        ArticuloEntity classic = articuloRepository.findByDenominacion("Classic")
                .orElseThrow(() -> new RuntimeException("No se encontró Classic"));

        ArticuloEntity papas = articuloRepository.findByDenominacion("Papas Fritas")
                .orElseThrow(() -> new RuntimeException("No se encontró Papas Fritas"));

        ArticuloEntity cerveza = articuloRepository.findByDenominacion("Cerveza 375ml")
                .orElseThrow(() -> new RuntimeException("No se encontró Cerveza 375ml"));

        ArticuloEntity nachos = articuloRepository.findByDenominacion("Nachos con Guacamole")
                .orElseThrow(() -> new RuntimeException("No se encontró Nachos"));

        ArticuloEntity batatas = articuloRepository.findByDenominacion("Batatas Crocantes")
                .orElseThrow(() -> new RuntimeException("No se encontró Batatas"));

        ArticuloEntity softMayo = articuloRepository.findByDenominacion("Soft Mayo")
                .orElseThrow(() -> new RuntimeException("No se encontró Soft Mayo"));

        List<ArticuloPromocionEntity> promociones = new ArrayList<>();

        ArticuloPromocionEntity combo1 = crearPromocion(
                "Combo BBQ + Coca Cola",
                "Hamburguesa BBQ con Coca Cola 500ml",
                List.of(
                        detalle(bbq, 1),
                        detalle(cocaCola, 1)
                ),
                sucursal,
                25,
                LocalDate.now(),
                LocalDate.now().plusMonths(12),
                LocalTime.of(0, 0),
                LocalTime.of(23, 0),
                comboIndividual
        );
        combo1.setImagenesUrls(crearImagenes(combo1, "https://res.cloudinary.com/drqdadlel/image/upload/v1749838093/gwiq22zgfprwzknnsnru.webp"));
        promociones.add(combo1);

        ArticuloPromocionEntity combo2 = crearPromocion(
                "Combo Classic Doble",
                "2 Hamburguesas Classic, 1 Papas Fritas y 2 Cervezas",
                List.of(
                        detalle(classic, 2),
                        detalle(papas, 1),
                        detalle(cerveza, 2)
                ),
                sucursal,
                30,
                LocalDate.now(),
                LocalDate.now().plusMonths(12),
                LocalTime.of(11, 0),
                LocalTime.of(23, 0),
                comboFamiliar
        );
        combo2.setImagenesUrls(crearImagenes(combo2, "https://res.cloudinary.com/drqdadlel/image/upload/v1749838093/kautfm8lturhgcsufeno.webp"));
        promociones.add(combo2);

        ArticuloPromocionEntity combo3 = crearPromocion(
                "Nachos + Coca",
                "Nachos con Coca Cola 500ml",
                List.of(
                        detalle(nachos, 1),
                        detalle(cocaCola, 1)
                ),
                sucursal,
                10,
                LocalDate.now(),
                LocalDate.now().plusMonths(12),
                LocalTime.of(17, 0),
                LocalTime.of(22, 0),
                comboIndividual
        );
        combo3.setImagenesUrls(crearImagenes(combo3, "https://res.cloudinary.com/drqdadlel/image/upload/v1749838093/estguhkiaxdaztpjw5ma.webp"));
        promociones.add(combo3);

        ArticuloPromocionEntity combo4 = crearPromocion(
                "Soft Mayo + Batatas + Cerveza",
                "Hamburguesa Soft Mayo con batatas y cerveza",
                List.of(
                        detalle(softMayo, 1),
                        detalle(batatas, 1),
                        detalle(cerveza, 1)
                ),
                sucursal,
                20,
                LocalDate.now(),
                LocalDate.now().plusMonths(12),
                LocalTime.of(18, 0),
                LocalTime.of(23, 59),
                comboIndividual
        );
        combo4.setImagenesUrls(crearImagenes(combo4, "https://res.cloudinary.com/drqdadlel/image/upload/v1749838092/wguftynzlcjwpxikbnaq.webp"));
        promociones.add(combo4);

        articuloPromocionRepository.saveAll(promociones);
    }

    private Set<ImagenArticuloEntity> crearImagenes(ArticuloPromocionEntity articulo, String... urls) {
        return Arrays.stream(urls)
                .map(url -> {
                    ImagenArticuloEntity imagen = ImagenArticuloEntity.builder()
                            .url(url)
                            .articulo(articulo)
                            .build();
                    return imagen;
                })
                .collect(Collectors.toSet());
    }

    private PromocionDetalleEntity detalle(ArticuloEntity articulo, int cantidad) {
        return PromocionDetalleEntity.builder()
                .articulo(articulo)
                .cantidad(cantidad)
                .build();
    }

    private ArticuloPromocionEntity crearPromocion(
            String nombre,
            String descripcion,
            List<PromocionDetalleEntity> detalles,
            SucursalEntity sucursal,
            int tiempoEstimado,
            LocalDate desde,
            LocalDate hasta,
            LocalTime horaDesde,
            LocalTime horaHasta,
            CategoriaEntity categoria
    ) {

        // Crear la promoción
        ArticuloPromocionEntity promocion = ArticuloPromocionEntity.builder()
                .denominacion(nombre)
                .descripcionDescuento(descripcion)
                .productoActivo(true)
                .categoria(categoria)
                .tiempoEstimadoMinutos(tiempoEstimado)
                .fechaDesde(desde)
                .fechaHasta(hasta)
                .horaDesde(horaDesde)
                .horaHasta(horaHasta)
                .sucursal(sucursal)
                .build();

        // Asociar detalles con la promoción
        detalles.forEach(d -> d.setArticuloPromocion(promocion));
        promocion.setPromocionDetalle(detalles);


        promocion.tiempoEstimadoCalculado(5);
        promocion.calcularPrecioCosto();
        promocion.calcularPrecioVenta();
        promocion.calcularPrecioPromocional();

        return promocion;
    }
}