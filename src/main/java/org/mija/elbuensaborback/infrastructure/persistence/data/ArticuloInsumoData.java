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
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ArticuloInsumoData {
    private final ArticuloInsumoJpaRepository articuloInsumoRepository;
    private final SucursalJpaRepository sucursalRepository;
    private final CategoriaJpaRepository categoriaRepository;

    public void initArticulosInsumos() {

        SucursalEntity sucursalEntity1 = sucursalRepository.findById(1L).orElseThrow(()->new EntityNotFoundException("No se encontro la sucursal"));

        //ARTICULOS INSUMOS
        ArticuloInsumoEntity panHamburguesa = ArticuloInsumoEntity.builder()
                .denominacion("Pan de Hamburguesa")
                .precioCompra(new BigDecimal("1200")) // por 1000 gr (1.2 kg), precio de panificado
                .precioVenta(new BigDecimal("1600"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .tiempoEstimadoMinutos(3)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("panaderia"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity medallonCarne = ArticuloInsumoEntity.builder()
                .denominacion("Medallón de Carne")
                .precioCompra(new BigDecimal("3500")) // por 1000 gr (carne molida buena)
                .precioVenta(new BigDecimal("4800"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .tiempoEstimadoMinutos(5)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("carnes"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity quesoCheddar = ArticuloInsumoEntity.builder()
                .denominacion("Queso Cheddar")
                .precioCompra(new BigDecimal("2200")) // por 1000 gr
                .precioVenta(new BigDecimal("3000"))
                .tiempoEstimadoMinutos(20)
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .tiempoEstimadoMinutos(2)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("lacteos"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity lechuga = ArticuloInsumoEntity.builder()
                .denominacion("Lechuga")
                .precioCompra(new BigDecimal("800")) // por 1000 gr
                .precioVenta(new BigDecimal("1000"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .tiempoEstimadoMinutos(1)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("verduras"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity tomate = ArticuloInsumoEntity.builder()
                .denominacion("Tomate")
                .precioCompra(new BigDecimal("900")) // por 1000 gr
                .precioVenta(new BigDecimal("1200"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .tiempoEstimadoMinutos(1)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("verduras"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity mayonesa = ArticuloInsumoEntity.builder()
                .denominacion("Mayonesa")
                .precioCompra(new BigDecimal("1500")) // por 1000 gr (botella grande)
                .precioVenta(new BigDecimal("2000"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .tiempoEstimadoMinutos(1)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("salsas"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity mostaza = ArticuloInsumoEntity.builder()
                .denominacion("Mostaza")
                .precioCompra(new BigDecimal("1400"))
                .precioVenta(new BigDecimal("1900"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .tiempoEstimadoMinutos(1)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("salsas"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity sal = ArticuloInsumoEntity.builder()
                .denominacion("Sal")
                .precioCompra(new BigDecimal("500")) // por 1000 gr
                .precioVenta(new BigDecimal("800"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .tiempoEstimadoMinutos(0)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("condimentos"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity bacon = ArticuloInsumoEntity.builder()
                .denominacion("Bacon")
                .precioCompra(new BigDecimal("3200")) // por 1000 gr
                .precioVenta(new BigDecimal("4500"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .tiempoEstimadoMinutos(3)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("carnes"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity panArtesanal = ArticuloInsumoEntity.builder()
                .denominacion("Pan artesanal")
                .precioCompra(new BigDecimal("1500"))
                .precioVenta(new BigDecimal("2000"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .tiempoEstimadoMinutos(1)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("panaderia"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity cebollaCaramelizada = ArticuloInsumoEntity.builder()
                .denominacion("Cebolla caramelizada")
                .precioCompra(new BigDecimal("1000"))
                .precioVenta(new BigDecimal("1400"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .tiempoEstimadoMinutos(5)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("verduras"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity queso = ArticuloInsumoEntity.builder()
                .denominacion("Queso")
                .precioCompra(new BigDecimal("2100")) // gral
                .precioVenta(new BigDecimal("2800"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .tiempoEstimadoMinutos(2)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("lacteos"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity salsaBarbacoa = ArticuloInsumoEntity.builder()
                .denominacion("Salsa barbacoa")
                .precioCompra(new BigDecimal("1300")) // por 1000 ml
                .precioVenta(new BigDecimal("1800"))
                .unidadMedidaEnum(UnidadMedidaEnum.ML)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .tiempoEstimadoMinutos(1)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("salsas"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity cebollaFresca = ArticuloInsumoEntity.builder()
                .denominacion("Cebolla fresca")
                .precioCompra(new BigDecimal("700"))
                .precioVenta(new BigDecimal("950"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .tiempoEstimadoMinutos(2)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("verduras"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity cebollaCrujiente = ArticuloInsumoEntity.builder()
                .denominacion("Cebolla crujiente")
                .precioCompra(new BigDecimal("700"))
                .precioVenta(new BigDecimal("950"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .tiempoEstimadoMinutos(2)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("verduras"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity salsaEspecial = ArticuloInsumoEntity.builder()
                .denominacion("Salsa especial")
                .precioCompra(new BigDecimal("700"))
                .precioVenta(new BigDecimal("950"))
                .unidadMedidaEnum(UnidadMedidaEnum.ML)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .tiempoEstimadoMinutos(1)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("salsas"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity batatas = ArticuloInsumoEntity.builder()
                .denominacion("Batatas")
                .precioCompra(new BigDecimal("700"))
                .precioVenta(new BigDecimal("950"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .tiempoEstimadoMinutos(2)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("verduras"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity papas = ArticuloInsumoEntity.builder()
                .denominacion("Papas")
                .precioCompra(new BigDecimal("700"))
                .precioVenta(new BigDecimal("950"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .tiempoEstimadoMinutos(2)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("verduras"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity nachos = ArticuloInsumoEntity.builder()
                .denominacion("Nachos")
                .precioCompra(new BigDecimal("150"))
                .precioVenta(new BigDecimal("0"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .tiempoEstimadoMinutos(2)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("snacks"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity guacamole = ArticuloInsumoEntity.builder()
                .denominacion("Guacamole")
                .precioCompra(new BigDecimal("180"))
                .precioVenta(new BigDecimal("0"))
                .unidadMedidaEnum(UnidadMedidaEnum.ML)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .tiempoEstimadoMinutos(1)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("salsas"))
                .sucursal(sucursalEntity1)
                .build();
        //BEBIDAS

        ArticuloInsumoEntity cocaCola = ArticuloInsumoEntity.builder()
                .denominacion("Coca Cola 500ml")
                .precioCompra(new BigDecimal("1500"))
                .precioVenta(new BigDecimal("2000"))
                .unidadMedidaEnum(UnidadMedidaEnum.UNIDAD)
                .stockActual(400.00)
                .stockMaximo(400.00)
                .stockMinimo(100.00)
                .tiempoEstimadoMinutos(0)
                .esParaPreparar(false)
                .esVendible(true)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("gaseosas"))
                .sucursal(sucursalEntity1)
                .imagenesUrls(Set.of(
                        ImagenArticuloEntity.builder().url("https://res.cloudinary.com/drqdadlel/image/upload/v1746571979/cocacola_s4s5rg.png").build()
                ))
                .build();

        ArticuloInsumoEntity cerveza = ArticuloInsumoEntity.builder()
                .denominacion("Cerveza 375ml")
                .precioCompra(new BigDecimal("2000"))
                .precioVenta(new BigDecimal("2500"))
                .unidadMedidaEnum(UnidadMedidaEnum.UNIDAD)
                .stockActual(400.00)
                .stockMaximo(400.00)
                .stockMinimo(100.00)
                .tiempoEstimadoMinutos(2)
                .esParaPreparar(false)
                .esVendible(true)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("cervezas"))
                .sucursal(sucursalEntity1)
                .imagenesUrls(Set.of(
                        ImagenArticuloEntity.builder().url("https://res.cloudinary.com/drqdadlel/image/upload/v1746571980/cerveza_dzrpj9.png").build()
                ))
                .build();

        ArticuloInsumoEntity aguaMineral = ArticuloInsumoEntity.builder()
                .denominacion("Agua Mineral 473ml")
                .precioCompra(new BigDecimal("1000"))
                .precioVenta(new BigDecimal("1500"))
                .unidadMedidaEnum(UnidadMedidaEnum.UNIDAD)
                .stockActual(400.00)
                .stockMaximo(400.00)
                .stockMinimo(100.00)
                .tiempoEstimadoMinutos(0)
                .esParaPreparar(false)
                .esVendible(true)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("aguas"))
                .sucursal(sucursalEntity1)
                .imagenesUrls(Set.of(
                        ImagenArticuloEntity.builder().url("https://res.cloudinary.com/drqdadlel/image/upload/v1746571980/aguamineral_gtoc1a.png").build()
                ))
                .build();

        articuloInsumoRepository.saveAll(List.of(bacon, panArtesanal, cebollaCaramelizada, queso,
                salsaBarbacoa, cebollaFresca, cebollaCrujiente,
                salsaEspecial, batatas, papas, nachos, guacamole,
                panHamburguesa, medallonCarne, quesoCheddar,
                lechuga, tomate, mayonesa, mostaza, sal, aguaMineral, cerveza, cocaCola
        ));
    }
}
