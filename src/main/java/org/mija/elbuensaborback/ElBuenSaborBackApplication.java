package org.mija.elbuensaborback;

import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;
import org.mija.elbuensaborback.domain.repository.*;
import org.mija.elbuensaborback.infrastructure.persistence.entity.*;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@SpringBootApplication
public class ElBuenSaborBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElBuenSaborBackApplication.class, args);
    }



    @Bean
    CommandLineRunner init(
            PaisJpaRepository paisRepository,
            ProvinciaJpaRepository provinciaRepository,
            LocalidadJpaRepository localidadRepository,
            EmpresaJpaRepository empresaRepository,
            CategoriaJpaRepository categoriaRepository,
            ArticuloInsumoJpaRepository articuloInsumoRepository
    ) {

        return args -> {
            // Crear y guardar países
            PaisEntity argentina = paisRepository.save(PaisEntity.builder()
                    .nombre("Argentina")
                    .build());

            PaisEntity chile = paisRepository.save(PaisEntity.builder()
                    .nombre("Chile")
                    .build());

            // Crear y guardar provincias (usando el país persistido)
            ProvinciaEntity mendoza = provinciaRepository.save(ProvinciaEntity.builder()
                    .nombre("Mendoza")
                    .pais(argentina)
                    .build());

            // Crear localidades (usando la provincia persistida)
            LocalidadEntity godoyCruz = LocalidadEntity.builder()
                    .nombre("Godoy Cruz")
                    .provincia(mendoza)
                    .build();

            LocalidadEntity lujanDeCuyo = LocalidadEntity.builder()
                    .nombre("Luján de Cuyo")
                    .provincia(mendoza)
                    .build();

            LocalidadEntity maipu = LocalidadEntity.builder()
                    .nombre("Maipú")
                    .provincia(mendoza)
                    .build();

            LocalidadEntity lasHeras = LocalidadEntity.builder()
                    .nombre("Las Heras")
                    .provincia(mendoza)
                    .build();

            localidadRepository.saveAll(List.of(godoyCruz, lujanDeCuyo, maipu, lasHeras));

            // Usar la localidad persistida
            SucursalEntity sucursalEntity1 = SucursalEntity.builder()
                    .nombre("PanchosSupremos Las Heras")
                    .horarioApertura(LocalTime.of(20, 0, 0))
                    .horarioCierre(LocalTime.of(1, 0, 0))
                    .domicilio(DomicilioEntity.builder().calle("Av.Pepe").numero(123).codigoPostal("5523").localidad(lasHeras).build())
                    .build();

            EmpresaEntity empresaEntity1 = EmpresaEntity.builder()
                    .cuil(232323232)
                    .nombre("PanchosSupremos")
                    .razonSocial("PanchosSupremos")
                    .listaSucursal(List.of(sucursalEntity1))
                    .build();

            sucursalEntity1.setEmpresa(empresaEntity1);

            empresaRepository.save(empresaEntity1);

            // CATEGORIAS
            CategoriaEntity comidas = CategoriaEntity.builder().denominacion("Comidas").build();
            CategoriaEntity pizzas = CategoriaEntity.builder().denominacion("Pizzas").categoriaPadre(comidas).build();
            CategoriaEntity hamburguesas = CategoriaEntity.builder().denominacion("Hamburguesas").categoriaPadre(comidas).build();
            CategoriaEntity hamburguesasVeganas = CategoriaEntity.builder().denominacion("Hamburguesas Veganas").categoriaPadre(comidas).categoriaPadre(hamburguesas).build();
            CategoriaEntity empanadas = CategoriaEntity.builder().denominacion("Empanadas").categoriaPadre(comidas).build();
            CategoriaEntity pastas = CategoriaEntity.builder().denominacion("Pastas").categoriaPadre(comidas).build();

            CategoriaEntity postres = CategoriaEntity.builder().denominacion("Postres").build();
            CategoriaEntity helados = CategoriaEntity.builder().denominacion("Helados").categoriaPadre(postres).build();
            CategoriaEntity tortas = CategoriaEntity.builder().denominacion("Tortas").categoriaPadre(postres).build();
            CategoriaEntity flanes = CategoriaEntity.builder().denominacion("Flanes y Budines").categoriaPadre(postres).build();

            CategoriaEntity combos = CategoriaEntity.builder().denominacion("Combos").build();
            CategoriaEntity comboFamiliar = CategoriaEntity.builder().denominacion("Combo Familiar").categoriaPadre(combos).build();
            CategoriaEntity comboIndividual = CategoriaEntity.builder().denominacion("Combo Individual").categoriaPadre(combos).build();

            CategoriaEntity ensaladas = CategoriaEntity.builder().denominacion("Ensaladas").build();
            CategoriaEntity ensaladasClasicas = CategoriaEntity.builder().denominacion("Clásicas").categoriaPadre(ensaladas).build();
            CategoriaEntity ensaladasEspeciales = CategoriaEntity.builder().denominacion("Especiales").categoriaPadre(ensaladas).build();

            CategoriaEntity insumos = CategoriaEntity.builder().denominacion("Insumos").build();
            CategoriaEntity salsas = CategoriaEntity.builder().denominacion("Salsas").categoriaPadre(insumos).build();
            CategoriaEntity panaderia = CategoriaEntity.builder().denominacion("Panadería").categoriaPadre(insumos).build();
            CategoriaEntity lacteos = CategoriaEntity.builder().denominacion("Lácteos").categoriaPadre(insumos).build();
            CategoriaEntity verduras = CategoriaEntity.builder().denominacion("Verduras").categoriaPadre(insumos).build();
            CategoriaEntity carnes = CategoriaEntity.builder().denominacion("Carnes").categoriaPadre(insumos).build();
            CategoriaEntity condimentos = CategoriaEntity.builder().denominacion("Condimentos").categoriaPadre(insumos).build();

            CategoriaEntity bebidas = CategoriaEntity.builder().denominacion("Bebidas").categoriaPadre(insumos).build();
            CategoriaEntity gaseosas = CategoriaEntity.builder().denominacion("Gaseosas").categoriaPadre(bebidas).build();
            CategoriaEntity aguas = CategoriaEntity.builder().denominacion("Aguas").categoriaPadre(bebidas).build();
            CategoriaEntity cervezas = CategoriaEntity.builder().denominacion("Cervezas").categoriaPadre(bebidas).build();
            CategoriaEntity jugos = CategoriaEntity.builder().denominacion("Jugos").categoriaPadre(bebidas).build();

            categoriaRepository.saveAll(List.of(
                    comidas, pizzas, hamburguesas, hamburguesasVeganas, empanadas, pastas,
                    postres, helados, tortas, flanes,
                    combos, comboFamiliar, comboIndividual,
                    ensaladas, ensaladasClasicas, ensaladasEspeciales,
                    insumos, salsas, panaderia, lacteos, verduras, carnes, condimentos,
                    bebidas, gaseosas, aguas, cervezas, jugos
            ));

            //ARTICULOS INSUMOS
            ArticuloInsumoEntity panHamburguesa = ArticuloInsumoEntity.builder()
                    .denominacion("Pan de Hamburguesa")
                    .precioCompra(new BigDecimal("100"))
                    .precioVenta(new BigDecimal("0"))
                    .unidadMedidaEnum(UnidadMedidaEnum.GR)
                    .stockActual(200.0)
                    .stockMaximo(300.0)
                    .stockMinimo(50.0)
                    .esParaPreparar(true)
                    .esVendible(false)
                    .productoActivo(true)
                    .categoria(panaderia)
                    .sucursal(sucursalEntity1)
                    .build();

            ArticuloInsumoEntity medallonCarne = ArticuloInsumoEntity.builder()
                    .denominacion("Medallón de Carne")
                    .precioCompra(new BigDecimal("350"))
                    .precioVenta(new BigDecimal("0"))
                    .unidadMedidaEnum(UnidadMedidaEnum.GR)
                    .stockActual(100.0)
                    .stockMaximo(150.0)
                    .stockMinimo(20.0)
                    .esParaPreparar(true)
                    .esVendible(false)
                    .productoActivo(true)
                    .categoria(carnes)
                    .sucursal(sucursalEntity1)
                    .build();

            ArticuloInsumoEntity quesoCheddar = ArticuloInsumoEntity.builder()
                    .denominacion("Queso Cheddar")
                    .precioCompra(new BigDecimal("200"))
                    .precioVenta(new BigDecimal("0"))
                    .unidadMedidaEnum(UnidadMedidaEnum.GR)
                    .stockActual(1000.0)
                    .stockMaximo(2000.0)
                    .stockMinimo(200.0)
                    .esParaPreparar(true)
                    .esVendible(false)
                    .productoActivo(true)
                    .categoria(lacteos)
                    .sucursal(sucursalEntity1)
                    .build();

            ArticuloInsumoEntity lechuga = ArticuloInsumoEntity.builder()
                    .denominacion("Lechuga")
                    .precioCompra(new BigDecimal("50"))
                    .precioVenta(new BigDecimal("0"))
                    .unidadMedidaEnum(UnidadMedidaEnum.GR)
                    .stockActual(500.0)
                    .stockMaximo(1000.0)
                    .stockMinimo(100.0)
                    .esParaPreparar(true)
                    .esVendible(false)
                    .productoActivo(true)
                    .categoria(verduras)
                    .sucursal(sucursalEntity1)
                    .build();

            ArticuloInsumoEntity tomate = ArticuloInsumoEntity.builder()
                    .denominacion("Tomate")
                    .precioCompra(new BigDecimal("70"))
                    .precioVenta(new BigDecimal("0"))
                    .unidadMedidaEnum(UnidadMedidaEnum.GR)
                    .stockActual(500.0)
                    .stockMaximo(1000.0)
                    .stockMinimo(100.0)
                    .esParaPreparar(true)
                    .esVendible(false)
                    .productoActivo(true)
                    .categoria(verduras)
                    .sucursal(sucursalEntity1)
                    .build();

            ArticuloInsumoEntity mayonesa = ArticuloInsumoEntity.builder()
                    .denominacion("Mayonesa")
                    .precioCompra(new BigDecimal("60"))
                    .precioVenta(new BigDecimal("0"))
                    .unidadMedidaEnum(UnidadMedidaEnum.GR)
                    .stockActual(300.0)
                    .stockMaximo(800.0)
                    .stockMinimo(100.0)
                    .esParaPreparar(true)
                    .esVendible(false)
                    .productoActivo(true)
                    .categoria(salsas)
                    .sucursal(sucursalEntity1)
                    .build();

            ArticuloInsumoEntity mostaza = ArticuloInsumoEntity.builder()
                    .denominacion("Mostaza")
                    .precioCompra(new BigDecimal("55"))
                    .precioVenta(new BigDecimal("0"))
                    .unidadMedidaEnum(UnidadMedidaEnum.GR)
                    .stockActual(200.0)
                    .stockMaximo(600.0)
                    .stockMinimo(80.0)
                    .esParaPreparar(true)
                    .esVendible(false)
                    .productoActivo(true)
                    .categoria(salsas)
                    .sucursal(sucursalEntity1)
                    .build();

            ArticuloInsumoEntity sal = ArticuloInsumoEntity.builder()
                    .denominacion("Sal")
                    .precioCompra(new BigDecimal("20"))
                    .precioVenta(new BigDecimal("0"))
                    .unidadMedidaEnum(UnidadMedidaEnum.GR)
                    .stockActual(1000.0)
                    .stockMaximo(2000.0)
                    .stockMinimo(200.0)
                    .esParaPreparar(true)
                    .esVendible(false)
                    .productoActivo(true)
                    .categoria(condimentos)
                    .sucursal(sucursalEntity1)
                    .build();

            articuloInsumoRepository.saveAll(List.of(
                    panHamburguesa, medallonCarne, quesoCheddar,
                    lechuga, tomate, mayonesa, mostaza, sal
            ));
        };

    }
}
