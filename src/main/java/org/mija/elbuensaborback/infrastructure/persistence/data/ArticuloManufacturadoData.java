package org.mija.elbuensaborback.infrastructure.persistence.data;

import jakarta.persistence.EntityNotFoundException;
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
public class ArticuloManufacturadoData {

    private final SucursalJpaRepository sucursalRepository;
    private final CategoriaJpaRepository categoriaRepository;
    private final ArticuloInsumoJpaRepository articuloInsumoRepository;
    private final ArticuloManufacturadoJpaRepository articuloManufacturadoRepository;

    public ArticuloManufacturadoData(SucursalJpaRepository sucursalRepository, CategoriaJpaRepository categoriaRepository, ArticuloInsumoJpaRepository articuloInsumoRepository, ArticuloManufacturadoJpaRepository articuloManufacturadoRepository) {
        this.sucursalRepository = sucursalRepository;
        this.categoriaRepository = categoriaRepository;
        this.articuloInsumoRepository = articuloInsumoRepository;
        this.articuloManufacturadoRepository = articuloManufacturadoRepository;
    }


    public void initArticulosManufacturados() {
        SucursalEntity sucursal = sucursalRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la sucursal"));

        CategoriaEntity hamburguesas = categoriaRepository.findByDenominacion("hamburguesas");
        CategoriaEntity sides = categoriaRepository.findByDenominacion("sides");

        ArticuloInsumoEntity pan = articuloInsumoRepository.findByDenominacion("Pan de Hamburguesa");
        ArticuloInsumoEntity carne = articuloInsumoRepository.findByDenominacion("Medallón de carne");
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

        articulos.add(crearArticuloManufacturado("delish",
                "Hamburguesa jugosa con queso cheddar, bacon crujiente y una salsa especial.",
                new BigDecimal("8000"), 15,
                "https://res.cloudinary.com/drqdadlel/image/upload/v1726104199/delish1_x3s5yl.png",
                hamburguesas, sucursal,
                List.of(
                        detalle(carne, 120),         // 120g de carne
                        detalle(cheddar, 25),        // 25g de queso
                        detalle(bacon, 20),          // 20g de bacon
                        detalle(panArtesanal, 90),   // 90g de pan
                        detalle(salsaEspecial, 15)   // 15g de salsa
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

        articulos.add(crearArticuloManufacturado("classic",
                "Hamburguesa clásica con lechuga, tomate, queso cheddar y mayonesa.",
                new BigDecimal("7800"), 14,
                "https://res.cloudinary.com/drqdadlel/image/upload/v1726104199/classic_bsdz9q.png",
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

        articulos.add(crearArticuloManufacturado("soft mayo",
                "Hamburguesa suave con mayonesa, queso y carne de res.",
                new BigDecimal("7500"), 13,
                "https://res.cloudinary.com/drqdadlel/image/upload/v1726104200/softmayo_fd9xos.png",
                hamburguesas, sucursal,
                List.of(
                        detalle(carne, 120),
                        detalle(cheddar, 25),
                        detalle(mayonesa, 15),
                        detalle(pan, 90)
                )
        ));

        articulos.add(crearArticuloManufacturado("crispy onion",
                "Hamburguesa con carne, cebolla crujiente y mostaza.",
                new BigDecimal("8200"), 14,
                "https://res.cloudinary.com/drqdadlel/image/upload/v1726104199/crispy_kjv0r5.png",
                hamburguesas, sucursal,
                List.of(
                        detalle(carne, 120),
                        detalle(cebollaCrujiente, 30),
                        detalle(mostaza, 10),
                        detalle(pan, 90)
                )
        ));

        articulos.add(crearArticuloManufacturado("batatas",
                "Porción de batatas fritas con sal.",
                new BigDecimal("3000"), 8,
                "https://res.cloudinary.com/drqdadlel/image/upload/v1726104200/batatas_bajyaj.png",
                sides, sucursal,
                List.of(
                        detalle(batatas, 150),
                        detalle(sal, 5)
                )
        ));

        articulos.add(crearArticuloManufacturado("papas fritas",
                "Porción de papas fritas crocantes con sal.",
                new BigDecimal("2500"), 8,
                "https://res.cloudinary.com/drqdadlel/image/upload/v1726104200/papas_hus6re.png",
                sides, sucursal,
                List.of(
                        detalle(papas, 150),
                        detalle(sal, 5)
                )
        ));

        articulos.add(crearArticuloManufacturado("nachos",
                "Nachos crocantes acompañados de guacamole y queso.",
                new BigDecimal("4000"), 10,
                "https://res.cloudinary.com/drqdadlel/image/upload/v1726104200/nachos_keg1uu.png",
                sides, sucursal,
                List.of(
                        detalle(nachos, 100),
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
        articulo.precioCostoCalculado();
        return articulo;
    }
}
