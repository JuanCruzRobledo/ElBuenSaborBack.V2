package org.mija.elbuensaborback.infrastructure.persistence.data;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloInsumoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ImagenArticuloEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.SucursalEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ArticuloInsumoJpaRepository;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.CategoriaJpaRepository;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.SucursalJpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticuloInsumoVendibleData {
    private final ArticuloInsumoJpaRepository articuloInsumoRepository;
    private final SucursalJpaRepository sucursalRepository;
    private final CategoriaJpaRepository categoriaRepository;

    private Set<ImagenArticuloEntity> crearImagenes(ArticuloInsumoEntity articulo, String... urls) {
        return Arrays.stream(urls)
                .map(url -> ImagenArticuloEntity.builder()
                        .url(url)
                        .articulo(articulo)
                        .build())
                .collect(Collectors.toSet());
    }

    private ArticuloInsumoEntity crearInsumoVendible(String nombre, String categoria, BigDecimal precioCosto,
                                                     BigDecimal precioVenta, UnidadMedidaEnum unidad,
                                                     int tiempo, SucursalEntity sucursal, String... imageUrls) {
        ArticuloInsumoEntity insumo = ArticuloInsumoEntity.builder()
                .denominacion(nombre)
                .precioCosto(precioCosto)
                .precioVenta(precioVenta)
                .unidadMedidaEnum(unidad)
                .stockActual(400.0)
                .stockMaximo(400.0)
                .stockMinimo(100.0)
                .tiempoEstimadoMinutos(tiempo)
                .esParaPreparar(false)
                .esVendible(true)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion(categoria))
                .sucursal(sucursal)
                .build();

        insumo.setImagenesUrls(crearImagenes(insumo, imageUrls));
        return insumo;
    }

    public void initArticulosInsumosVendibles() {
        SucursalEntity sucursal = sucursalRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la sucursal"));

        List<ArticuloInsumoEntity> insumos = new ArrayList<>();

        insumos.add(crearInsumoVendible("Coca Cola 500ml", "Gaseosas",
                new BigDecimal("1500"), new BigDecimal("2000"), UnidadMedidaEnum.UNIDAD, 0,
                sucursal, "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894299/Coca_Cola_ih4cvn.webp"));

        insumos.add(crearInsumoVendible("Cerveza 375ml", "Cervezas",
                new BigDecimal("2000"), new BigDecimal("2500"), UnidadMedidaEnum.UNIDAD, 2,
                sucursal, "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894297/Cerveza_okgdk9.webp"));

        insumos.add(crearInsumoVendible("Agua Mineral 473ml", "Aguas",
                new BigDecimal("1000"), new BigDecimal("1500"), UnidadMedidaEnum.UNIDAD, 0,
                sucursal, "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894295/Agua_mineral_ywy3zt.webp"));

        insumos.add(crearInsumoVendible("Sprite 500ml", "Gaseosas",
                new BigDecimal("1400"), new BigDecimal("1900"), UnidadMedidaEnum.UNIDAD, 0,
                sucursal, "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894505/Sprite_cvowc1.webp"));

        insumos.add(crearInsumoVendible("Pepsi 500ml", "Gaseosas",
                new BigDecimal("1350"), new BigDecimal("1850"), UnidadMedidaEnum.UNIDAD, 0,
                sucursal, "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894304/Pepsi_j1v4c8.webp"));

        insumos.add(crearInsumoVendible("Fanta Naranja 500ml", "Gaseosas",
                new BigDecimal("1400"), new BigDecimal("1900"), UnidadMedidaEnum.UNIDAD, 0,
                sucursal, "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894301/Fanta_y2zy9q.webp"));

        insumos.add(crearInsumoVendible("Cerveza IPA 473ml", "Cervezas",
                new BigDecimal("2500"), new BigDecimal("3200"), UnidadMedidaEnum.UNIDAD, 2,
                sucursal, "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894297/Cerveza_IPA_dz8e7h.webp"));

        insumos.add(crearInsumoVendible("Jugo de Naranja 500ml", "Jugos",
                new BigDecimal("1200"), new BigDecimal("1700"), UnidadMedidaEnum.UNIDAD, 0,
                sucursal, "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894298/Citric_de_naranja_joxtkp.webp"));

        insumos.add(crearInsumoVendible("Jugo de Manzana 500ml", "Jugos",
                new BigDecimal("1200"), new BigDecimal("1700"), UnidadMedidaEnum.UNIDAD, 0,
                sucursal, "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894296/Cepita_de_manzana_dlrkz6.webp"));

        insumos.add(crearInsumoVendible("Lata Energetica 250ml", "Energizantes",
                new BigDecimal("2300"), new BigDecimal("2800"), UnidadMedidaEnum.UNIDAD, 0,
                sucursal, "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894504/Red_Bull_z9myp5.webp"));

        insumos.add(crearInsumoVendible("Oreos", "Snacks",
                new BigDecimal("1000"), new BigDecimal("1600"), UnidadMedidaEnum.UNIDAD, 0,
                sucursal, "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894303/Oreos_iggjbu.webp"));

        insumos.add(crearInsumoVendible("Doritos 46g", "Snacks",
                new BigDecimal("1100"), new BigDecimal("1800"), UnidadMedidaEnum.UNIDAD, 0,
                sucursal, "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894301/Doritos_rtjtfo.webp"));

        insumos.add(crearInsumoVendible("Chocolate Block", "Snacks",
                new BigDecimal("2100"), new BigDecimal("4900"), UnidadMedidaEnum.UNIDAD, 0,
                sucursal, "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894297/Chocolate_Block_dg2pom.webp"));

        insumos.add(crearInsumoVendible("Alfajor B&N 3.0", "Snacks",
                new BigDecimal("700"), new BigDecimal("1500"), UnidadMedidaEnum.UNIDAD, 0,
                sucursal,
                "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894295/Alfajor_Blanco_vrdvcb.webp",
                "https://res.cloudinary.com/dcjr3qnze/image/upload/v1750894295/Alfajor_Negro_onsivm.webp"));


        // Guardamos todo
        articuloInsumoRepository.saveAll(insumos);
    }
}
