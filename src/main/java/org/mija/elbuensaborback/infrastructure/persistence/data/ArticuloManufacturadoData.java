package org.mija.elbuensaborback.infrastructure.persistence.data;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.infrastructure.persistence.entity.*;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ArticuloInsumoJpaRepository;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ArticuloManufacturadoJpaRepository;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.CategoriaJpaRepository;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.SucursalJpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ArticuloManufacturadoData {

    private final SucursalJpaRepository sucursalRepository;
    private final CategoriaJpaRepository categoriaRepository;
    private final ArticuloInsumoJpaRepository articuloInsumoRepository;
    private final ArticuloManufacturadoJpaRepository articuloManufacturadoRepository;

    public void initArticulosManufacturados() {
        SucursalEntity sucursal = sucursalRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la sucursal"));

        CategoriaEntity hamburguesas = categoriaRepository.findByDenominacion("hamburguesas");
        CategoriaEntity sides = categoriaRepository.findByDenominacion("sides");

        ArticuloInsumoEntity pan = articuloInsumoRepository.findByDenominacion("Pan de Hamburguesa");
        ArticuloInsumoEntity carne = articuloInsumoRepository.findByDenominacion("Medallón de carne");
        //demas
        ArticuloInsumoEntity cheddar = articuloInsumoRepository.findByDenominacion("Queso Cheddar");
        ArticuloInsumoEntity lechuga = articuloInsumoRepository.findByDenominacion("Lechuga");
        ArticuloInsumoEntity tomate = articuloInsumoRepository.findByDenominacion("Tomate");
        ArticuloInsumoEntity mayonesa = articuloInsumoRepository.findByDenominacion("Mayonesa");
        ArticuloInsumoEntity mostaza = articuloInsumoRepository.findByDenominacion("Mostaza");
        ArticuloInsumoEntity cebollaCaramelizada = articuloInsumoRepository.findByDenominacion("Cebolla caramelizada");
        ArticuloInsumoEntity cebollaCrujiente = articuloInsumoRepository.findByDenominacion("Cebolla crujiente");
        ArticuloInsumoEntity bacon = articuloInsumoRepository.findByDenominacion("Bacon");
        ArticuloInsumoEntity panArtesanal = articuloInsumoRepository.findByDenominacion("Pan artesanal");
        ArticuloInsumoEntity salsaEspecial = articuloInsumoRepository.findByDenominacion("Salsa especial");
        ArticuloInsumoEntity salsaBBQ = articuloInsumoRepository.findByDenominacion("Salsa barbacoa");
        ArticuloInsumoEntity batatas = articuloInsumoRepository.findByDenominacion("Batatas");
        ArticuloInsumoEntity papas = articuloInsumoRepository.findByDenominacion("Papas");
        ArticuloInsumoEntity nachos = articuloInsumoRepository.findByDenominacion("Nachos");
        ArticuloInsumoEntity guacamole = articuloInsumoRepository.findByDenominacion("Guacamole");
        ArticuloInsumoEntity sal = articuloInsumoRepository.findByDenominacion("Sal");
        ArticuloInsumoEntity queso = articuloInsumoRepository.findByDenominacion("Queso");

        List<ArticuloManufacturadoEntity> articulos = new ArrayList<>();

        articulos.add(crearArticuloManufacturado("Delish",
                "Hamburguesa jugosa con queso cheddar, bacon crujiente y una salsa especial.",
                new BigDecimal("8000"), 15,
                "https://res.cloudinary.com/drqdadlel/image/upload/v1726104199/delish1_x3s5yl.png",
                hamburguesas, sucursal,
                List.of(
                        detalle(carne, 120),
                        detalle(cheddar, 25),
                        detalle(bacon, 20),
                        detalle(panArtesanal, 90),
                        detalle(salsaEspecial, 15)
                )
        ));

        articulos.add(crearArticuloManufacturado("BBQ",
                "Hamburguesa con carne de res, cebolla caramelizada, queso y salsa barbacoa.",
                new BigDecimal("8500"), 16,
                "https://res.cloudinary.com/drqdadlel/image/upload/v1726104199/bbq_orgfao.png",
                hamburguesas, sucursal,
                List.of(
                        detalle(carne, 120),
                        detalle(cebollaCaramelizada, 30),
                        detalle(queso, 25),
                        detalle(pan, 90),
                        detalle(salsaBBQ, 20)
                )
        ));

        articulos.add(crearArticuloManufacturado("Classic",
                "Hamburguesa clásica con lechuga, tomate, queso cheddar y mayonesa.",
                new BigDecimal("7800"), 14,
                "https://res.cloudinary.com/drqdadlel/image/upload/v1726104199/classic_a9kzyk.png",
                hamburguesas, sucursal,
                List.of(
                        detalle(carne, 120),
                        detalle(lechuga, 20),
                        detalle(tomate, 30),
                        detalle(cheddar, 25),
                        detalle(pan, 90),
                        detalle(mayonesa, 15)
                )
        ));

        articulos.add(crearArticuloManufacturado("Soft Mayo",
                "Hamburguesa suave con mayonesa, queso y carne de res.",
                new BigDecimal("7500"), 13,
                "https://res.cloudinary.com/drqdadlel/image/upload/v1726104199/softMayo_sopgd7.png",
                hamburguesas, sucursal,
                List.of(
                        detalle(carne, 120),
                        detalle(cheddar, 25),
                        detalle(mayonesa, 15),
                        detalle(pan, 90)
                )
        ));

        articulos.add(crearArticuloManufacturado("Crispy Onion",
                "Hamburguesa con carne, cebolla crujiente y mostaza.",
                new BigDecimal("8200"), 14,
                "https://res.cloudinary.com/drqdadlel/image/upload/v1726104199/crispyOnion_iarzpq.png",
                hamburguesas, sucursal,
                List.of(
                        detalle(carne, 120),
                        detalle(cebollaCrujiente, 30),
                        detalle(mostaza, 10),
                        detalle(pan, 90)
                )
        ));

        articulos.add(crearArticuloManufacturado("Batatas Crocantes",
                "Porción de batatas fritas con sal.",
                new BigDecimal("3000"), 8,
                "https://res.cloudinary.com/drqdadlel/image/upload/v1726104198/batatas_pnzcgu.png",
                sides, sucursal,
                List.of(
                        detalle(batatas, 150),
                        detalle(sal, 5)
                )
        ));

        articulos.add(crearArticuloManufacturado("Papas Fritas",
                "Porción de papas fritas crocantes con sal.",
                new BigDecimal("2500"), 8,
                "https://res.cloudinary.com/drqdadlel/image/upload/v1726104198/papasSimples_rnms3w.png",
                sides, sucursal,
                List.of(
                        detalle(papas, 150),
                        detalle(sal, 5)
                )
        ));

        articulos.add(crearArticuloManufacturado("Nachos con Guacamole",
                "Nachos crocantes acompañados de guacamole y queso.",
                new BigDecimal("4000"), 10,
                "https://res.cloudinary.com/drqdadlel/image/upload/v1726104198/nachos_rbbtca.png",
                sides, sucursal,
                List.of(
                        detalle(nachos, 150),
                        detalle(guacamole, 50),
                        detalle(queso, 30)
                )
        ));

        articuloManufacturadoRepository.saveAll(articulos);
    }

    private ArticuloManufacturadoDetalleEntity detalle(ArticuloInsumoEntity insumo, double cantidad) {
        return ArticuloManufacturadoDetalleEntity.builder()
                .articuloInsumo(insumo)
                .cantidad(cantidad)
                .build();
    }

    private ArticuloManufacturadoEntity crearArticuloManufacturado(
            String denominacion,
            String descripcion,
            BigDecimal precio,
            int tiempo,
            String imagen,
            CategoriaEntity categoria,
            SucursalEntity sucursal,
            List<ArticuloManufacturadoDetalleEntity> detalles
    ) {
        ArticuloManufacturadoEntity articulo = ArticuloManufacturadoEntity.builder()
                .productoActivo(true)
                .esVendible(true)
                .denominacion(denominacion)
                .descripcion(descripcion)
                .precioVenta(precio)
                .tiempoEstimadoMinutos(tiempo)
                .categoria(categoria)
                .sucursal(sucursal)
                .build();

        detalles.forEach(d -> d.setArticuloManufacturado(articulo));
        articulo.setArticuloManufacturadoDetalle(detalles);
        articulo.setImagenesUrls(Set.of(ImagenArticuloEntity.builder().url(imagen).articulo(articulo).build()));
        articulo.tiempoEstimadoCalculado(5);
        articulo.calcularPrecioCosto();
        return articulo;
    }
}
