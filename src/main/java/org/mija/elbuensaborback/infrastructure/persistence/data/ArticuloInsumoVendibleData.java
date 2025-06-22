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

        insumos.add(crearInsumoVendible("Coca Cola 500ml", "gaseosas",
                new BigDecimal("1500"), new BigDecimal("2000"), UnidadMedidaEnum.UNIDAD, 0,
                sucursal, "https://res.cloudinary.com/drqdadlel/image/upload/v1746571979/cocacola_s4s5rg.png"));

        insumos.add(crearInsumoVendible("Cerveza 375ml", "cervezas",
                new BigDecimal("2000"), new BigDecimal("2500"), UnidadMedidaEnum.UNIDAD, 2,
                sucursal, "https://res.cloudinary.com/drqdadlel/image/upload/v1746571980/cerveza_dzrpj9.png"));

        insumos.add(crearInsumoVendible("Agua Mineral 473ml", "aguas",
                new BigDecimal("1000"), new BigDecimal("1500"), UnidadMedidaEnum.UNIDAD, 0,
                sucursal, "https://res.cloudinary.com/drqdadlel/image/upload/v1746571980/aguamineral_gtoc1a.png"));

        insumos.add(crearInsumoVendible("Sprite 500ml", "gaseosas",
                new BigDecimal("1400"), new BigDecimal("1900"), UnidadMedidaEnum.UNIDAD, 0,
                sucursal, "https://res.cloudinary.com/dlqx3atyg/image/upload/v1750605283/sprite_500ml_z1hvj3.webp"));

        insumos.add(crearInsumoVendible("Pepsi 500ml", "gaseosas",
                new BigDecimal("1350"), new BigDecimal("1850"), UnidadMedidaEnum.UNIDAD, 0,
                sucursal, "https://res.cloudinary.com/dlqx3atyg/image/upload/v1750605398/orgsize_15597Pepsi_20Regular_20Blue_20500ml_agwlfx.webp"));

        insumos.add(crearInsumoVendible("Fanta Naranja 500ml", "gaseosas",
                new BigDecimal("1400"), new BigDecimal("1900"), UnidadMedidaEnum.UNIDAD, 0,
                sucursal, "https://res.cloudinary.com/dlqx3atyg/image/upload/v1750605466/fanta_500ml_tjyime.png"));

        insumos.add(crearInsumoVendible("Cerveza IPA 473ml", "cervezas",
                new BigDecimal("2500"), new BigDecimal("3200"), UnidadMedidaEnum.UNIDAD, 2,
                sucursal, "https://res.cloudinary.com/dlqx3atyg/image/upload/v1750605553/Cerveza_IPA_pmsq92.png"));

        insumos.add(crearInsumoVendible("Jugo de Naranja 500ml", "jugos",
                new BigDecimal("1200"), new BigDecimal("1700"), UnidadMedidaEnum.UNIDAD, 0,
                sucursal, "https://res.cloudinary.com/dlqx3atyg/image/upload/v1750605992/citric-naranja-500ml_chdjuo.png"));

        insumos.add(crearInsumoVendible("Jugo de Manzana 500ml", "jugos",
                new BigDecimal("1200"), new BigDecimal("1700"), UnidadMedidaEnum.UNIDAD, 0,
                sucursal, "https://res.cloudinary.com/dlqx3atyg/image/upload/v1750605716/Jugo_manzana_500ml_eyhugv.png"));

        insumos.add(crearInsumoVendible("Lata Energética 250ml", "energizantes",
                new BigDecimal("2300"), new BigDecimal("2800"), UnidadMedidaEnum.UNIDAD, 0,
                sucursal, "https://res.cloudinary.com/dlqx3atyg/image/upload/v1750605759/Bebida-Energizante-Redbull-Lata-250ml_aw08q1.png"));

        insumos.add(crearInsumoVendible("Oreos", "snacks",
                new BigDecimal("1000"), new BigDecimal("1600"), UnidadMedidaEnum.UNIDAD, 0,
                sucursal, "https://res.cloudinary.com/dlqx3atyg/image/upload/v1750606169/oreos-1paquete_wapjcd.webp"));

        insumos.add(crearInsumoVendible("Doritos 46g", "snacks",
                new BigDecimal("1100"), new BigDecimal("1800"), UnidadMedidaEnum.UNIDAD, 0,
                sucursal, "https://res.cloudinary.com/dlqx3atyg/image/upload/v1750606373/doritos-packete_u6bfpe.png"));

        insumos.add(crearInsumoVendible("Chocolate Block", "chocolates",
                new BigDecimal("2100"), new BigDecimal("4900"), UnidadMedidaEnum.UNIDAD, 0,
                sucursal, "https://res.cloudinary.com/dlqx3atyg/image/upload/v1750606509/chocolate_block_w5xwdq.png"));

        insumos.add(crearInsumoVendible("Alfajor B&N 3.0", "alfajores",
                new BigDecimal("700"), new BigDecimal("1500"), UnidadMedidaEnum.UNIDAD, 0,
                sucursal,
                "https://res.cloudinary.com/dlqx3atyg/image/upload/v1750607024/alfajor-bn-blanco_xjlvz9.png",
                "https://res.cloudinary.com/dlqx3atyg/image/upload/v1750607025/alfajor-bn-negro_jwcicl.png"));

        // Guardamos todo
        articuloInsumoRepository.saveAll(insumos);
    }
}
