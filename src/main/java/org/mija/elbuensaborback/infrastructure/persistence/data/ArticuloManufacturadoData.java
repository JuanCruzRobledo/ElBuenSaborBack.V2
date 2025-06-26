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

        ArticuloInsumoEntity pan = articuloInsumoRepository.findByDenominacion("Pan de Hamburguesa Comun");
        ArticuloInsumoEntity carne = articuloInsumoRepository.findByDenominacion("Medallon de Carne");
        ArticuloInsumoEntity cheddar = articuloInsumoRepository.findByDenominacion("Queso Cheddar");
        ArticuloInsumoEntity lechuga = articuloInsumoRepository.findByDenominacion("Lechuga");
        ArticuloInsumoEntity tomate = articuloInsumoRepository.findByDenominacion("Tomate");
        ArticuloInsumoEntity mayonesa = articuloInsumoRepository.findByDenominacion("Mayonesa");
        ArticuloInsumoEntity mostaza = articuloInsumoRepository.findByDenominacion("Mostaza");
        ArticuloInsumoEntity ketchup = articuloInsumoRepository.findByDenominacion("Ketchup");
        ArticuloInsumoEntity cebolla = articuloInsumoRepository.findByDenominacion("Cebolla");
        ArticuloInsumoEntity bacon = articuloInsumoRepository.findByDenominacion("Bacon");
        ArticuloInsumoEntity panArtesanal = articuloInsumoRepository.findByDenominacion("Pan artesanal");
        ArticuloInsumoEntity salsaEspecial = articuloInsumoRepository.findByDenominacion("Salsa especial");
        ArticuloInsumoEntity salsaBBQ = articuloInsumoRepository.findByDenominacion("Salsa barbacoa");
        ArticuloInsumoEntity batatas = articuloInsumoRepository.findByDenominacion("Batatas");
        ArticuloInsumoEntity papas = articuloInsumoRepository.findByDenominacion("Papas");
        ArticuloInsumoEntity nachos = articuloInsumoRepository.findByDenominacion("Nachos");
        ArticuloInsumoEntity guacamole = articuloInsumoRepository.findByDenominacion("Guacamole");
        ArticuloInsumoEntity sal = articuloInsumoRepository.findByDenominacion("Sal");
        ArticuloInsumoEntity quesoTibo = articuloInsumoRepository.findByDenominacion("Queso Tibo");
        ArticuloInsumoEntity pollo = articuloInsumoRepository.findByDenominacion("Pollo Deshuesado");
        ArticuloInsumoEntity harina = articuloInsumoRepository.findByDenominacion("Harina 000");
        ArticuloInsumoEntity huevo = articuloInsumoRepository.findByDenominacion("Huevo");
        ArticuloInsumoEntity panRallado = articuloInsumoRepository.findByDenominacion("Pan rallado");
        ArticuloInsumoEntity ajoPolvo = articuloInsumoRepository.findByDenominacion("Ajo en polvo");
        ArticuloInsumoEntity curry = articuloInsumoRepository.findByDenominacion("Curry");
        ArticuloInsumoEntity aceite = articuloInsumoRepository.findByDenominacion("Aceite");
        ArticuloInsumoEntity manteca = articuloInsumoRepository.findByDenominacion("Manteca");
        ArticuloInsumoEntity pimienta = articuloInsumoRepository.findByDenominacion("Pimienta");
        ArticuloInsumoEntity perejil = articuloInsumoRepository.findByDenominacion("Perejil fresco");
        ArticuloInsumoEntity panSesamo = articuloInsumoRepository.findByDenominacion("Pan con Sésamo");
        ArticuloInsumoEntity pepinillos = articuloInsumoRepository.findByDenominacion("Pepinillos");
        ArticuloInsumoEntity quesoVegano = articuloInsumoRepository.findByDenominacion("Queso vegano");
        ArticuloInsumoEntity medallonGarbanzos = articuloInsumoRepository.findByDenominacion("Medallon de Garbanzos");
        ArticuloInsumoEntity mayonesaZanahoria = articuloInsumoRepository.findByDenominacion("Mayonesa de Zanahoria");
        ArticuloInsumoEntity champiniones = articuloInsumoRepository.findByDenominacion("Champiñones");
        ArticuloInsumoEntity medallonChorizo = articuloInsumoRepository.findByDenominacion("Medallon de Chorizo");
        ArticuloInsumoEntity chimichurri = articuloInsumoRepository.findByDenominacion("Chimichurri");
        ArticuloInsumoEntity quesoProvolone = articuloInsumoRepository.findByDenominacion("Queso provolone");
        ArticuloInsumoEntity salsaPicante = articuloInsumoRepository.findByDenominacion("Salsa Picante");
        ArticuloInsumoEntity jalapenos = articuloInsumoRepository.findByDenominacion("Jalapeños");


        List<ArticuloManufacturadoEntity> articulos = new ArrayList<>();

        articulos.add(crearArticuloManufacturado("Delish",
                "Hamburguesa jugosa con queso cheddar, bacon crujiente y una salsa especial.",
                new BigDecimal("8000"), 15,
                "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894300/Delish_browzp.webp",
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
                "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894296/BBQ_uuubsy.webp",
                hamburguesas, sucursal,
                List.of(
                        detalle(carne, 120),
                        detalle(cebolla, 30),
                        detalle(quesoTibo, 25),
                        detalle(pan, 90),
                        detalle(salsaBBQ, 20)
                )
        ));

        articulos.add(crearArticuloManufacturado("Classic",
                "Hamburguesa clásica con lechuga, tomate, queso cheddar y mayonesa.",
                new BigDecimal("7800"), 14,
                "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894299/Classic_iro1or.webp",
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
                "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894504/Soft_Mayo_s0k42u.webp",
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
                "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894300/Crispy_kyijos.webp",
                hamburguesas, sucursal,
                List.of(
                        detalle(carne, 120),
                        detalle(cebolla, 30),
                        detalle(mostaza, 10),
                        detalle(pan, 90)
                )
        ));

        articulos.add(crearArticuloManufacturado("Batatas Crocantes",
                "Porción de batatas fritas con sal.",
                new BigDecimal("3000"), 8,
                "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894295/Batatas_l9mlrb.webp",
                sides, sucursal,
                List.of(
                        detalle(batatas, 150),
                        detalle(sal, 5)
                )
        ));

        articulos.add(crearArticuloManufacturado("Papas Fritas",
                "Porción de papas fritas crocantes con sal.",
                new BigDecimal("2500"), 8,
                "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894304/Papas_xfskj0.webp",
                sides, sucursal,
                List.of(
                        detalle(papas, 150),
                        detalle(sal, 5)
                )
        ));

        articulos.add(crearArticuloManufacturado("Nachos con Guacamole",
                "Nachos crocantes acompañados de guacamole y queso.",
                new BigDecimal("4000"), 10,
                "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894302/Nachos_a1vi1f.webp",
                sides, sucursal,
                List.of(
                        detalle(nachos, 150),
                        detalle(guacamole, 50),
                        detalle(quesoTibo, 30)
                )
        ));

        // Nuggets
        articulos.add(crearArticuloManufacturado("Nuggets",
                "Nuggets de pollo caseros con rebozado crocante.",
                new BigDecimal("4000"), 10,
                "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894303/Nuggets_z3ghll.webp",
                sides, sucursal,
                List.of(
                        detalle(pollo, 100),
                        detalle(harina, 20),
                        detalle(huevo, 1),
                        detalle(panRallado, 30),
                        detalle(ajoPolvo, 2),
                        detalle(curry, 2),
                        detalle(aceite, 10)
                )
        ));

        // Huevos Revueltos
        articulos.add(crearArticuloManufacturado("Huevos Revueltos",
                "Huevos revueltos con manteca y condimentos frescos.",
                new BigDecimal("3500"), 9,
                "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894301/Huevos_revueltos_wjo59h.webp",
                sides, sucursal,
                List.of(
                        detalle(huevo, 2),
                        detalle(manteca, 10),
                        detalle(sal, 1),
                        detalle(pimienta, 1),
                        detalle(perejil, 2)
                )
        ));

        // Bastones de Queso
        articulos.add(crearArticuloManufacturado("Bastones de Queso",
                "Bastones crocantes de queso tibo, rebozados y fritos.",
                new BigDecimal("4200"), 11,
                "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894295/Bastones_de_queso_umytq2.webp",
                sides, sucursal,
                List.of(
                        detalle(quesoTibo, 50),
                        detalle(aceite, 10),
                        detalle(panRallado, 30),
                        detalle(huevo, 1),
                        detalle(harina, 20),
                        detalle(sal, 1)
                )
        ));

        // Vegan Smash
        articulos.add(crearArticuloManufacturado("Vegan Smash",
                "Hamburguesa vegana con medallón de garbanzos y vegetales frescos.",
                new BigDecimal("7800"), 14,
                "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894295/Vegan_ynwghn.webp",
                hamburguesas, sucursal,
                List.of(
                        detalle(pan, 90),
                        detalle(medallonGarbanzos, 120),
                        detalle(mayonesaZanahoria, 15),
                        detalle(quesoVegano, 25),
                        detalle(cebolla, 20),
                        detalle(tomate, 30),
                        detalle(lechuga, 20),
                        detalle(champiniones, 20)
                )
        ));

        // McBurger
        articulos.add(crearArticuloManufacturado("McBurger",
                "Hamburguesa clásica con ingredientes frescos.",
                new BigDecimal("8200"), 15,
                "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894302/McBurger_rf6z1g.webp",
                hamburguesas, sucursal,
                List.of(
                        detalle(panSesamo, 90),
                        detalle(carne, 120),
                        detalle(pepinillos, 15),
                        detalle(cheddar, 25),
                        detalle(lechuga, 20),
                        detalle(cebolla, 20),
                        detalle(ketchup, 10),
                        detalle(mayonesa, 10)
                )
        ));

        // Spicy
        articulos.add(crearArticuloManufacturado("Spicy",
                "Hamburguesa picante con jalapeños y salsa especial.",
                new BigDecimal("8500"), 15,
                "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894505/Spicy_lfrd9g.webp",
                hamburguesas, sucursal,
                List.of(
                        detalle(pan, 90),
                        detalle(carne, 120),
                        detalle(pimienta, 1),
                        detalle(jalapenos, 15),
                        detalle(salsaPicante, 15)
                )
        ));

        // Choriburger
        articulos.add(crearArticuloManufacturado("Choriburger",
                "Hamburguesa con medallón de chorizo, provolone y chimichurri.",
                new BigDecimal("8700"), 15,
                "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894298/Choriburger_fpk5nv.webp",
                hamburguesas, sucursal,
                List.of(
                        detalle(panArtesanal, 90),
                        detalle(medallonChorizo, 120),
                        detalle(chimichurri, 10),
                        detalle(quesoProvolone, 25),
                        detalle(guacamole, 20)
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
