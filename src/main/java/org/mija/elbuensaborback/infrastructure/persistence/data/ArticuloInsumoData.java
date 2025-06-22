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
public class ArticuloInsumoData {
    private final ArticuloInsumoJpaRepository articuloInsumoRepository;
    private final SucursalJpaRepository sucursalRepository;
    private final CategoriaJpaRepository categoriaRepository;

    private Set<ImagenArticuloEntity> crearImagenes(ArticuloInsumoEntity articulo, String... urls) {
        return Arrays.stream(urls)
                .map(url -> {
                    ImagenArticuloEntity imagen = ImagenArticuloEntity.builder()
                            .url(url)
                            .articulo(articulo)  // establecemos el vínculo inverso
                            .build();
                    return imagen;
                })
                .collect(Collectors.toSet());
    }

    public void initArticulosInsumos() {

        //Lista de todos los insumos
        List<ArticuloInsumoEntity> insumos = new ArrayList<>();

        //ARTICULOS INSUMOS
        insumos.addAll(List.of(
                crearInsumo("Pan de Hamburguesa", "panaderia", new BigDecimal("1200"), new BigDecimal("1600"), UnidadMedidaEnum.GR, 3),
                crearInsumo("Medallón de Carne", "carnes", new BigDecimal("3500"), new BigDecimal("4800"), UnidadMedidaEnum.GR, 5),
                crearInsumo("Queso Cheddar", "lacteos", new BigDecimal("2200"), new BigDecimal("3000"), UnidadMedidaEnum.GR, 2),
                crearInsumo("Lechuga", "verduras", new BigDecimal("800"), new BigDecimal("1000"), UnidadMedidaEnum.GR, 1),
                crearInsumo("Tomate", "verduras", new BigDecimal("900"), new BigDecimal("1200"), UnidadMedidaEnum.GR, 1),
                crearInsumo("Mayonesa", "salsas", new BigDecimal("1500"), new BigDecimal("2000"), UnidadMedidaEnum.GR, 1),
                crearInsumo("Mostaza", "salsas", new BigDecimal("1400"), new BigDecimal("1900"), UnidadMedidaEnum.GR, 1),
                crearInsumo("Sal", "condimentos", new BigDecimal("500"), new BigDecimal("800"), UnidadMedidaEnum.GR, 0),
                crearInsumo("Bacon", "carnes", new BigDecimal("3200"), new BigDecimal("4500"), UnidadMedidaEnum.GR, 3),
                crearInsumo("Pan artesanal", "panaderia", new BigDecimal("1500"), new BigDecimal("2000"), UnidadMedidaEnum.GR, 1),
                crearInsumo("Cebolla", "verduras", new BigDecimal("1000"), new BigDecimal("1400"), UnidadMedidaEnum.GR, 5),
                crearInsumo("Queso", "lacteos", new BigDecimal("2100"), new BigDecimal("2800"), UnidadMedidaEnum.GR, 2),
                crearInsumo("Salsa barbacoa", "salsas", new BigDecimal("1300"), new BigDecimal("1800"), UnidadMedidaEnum.ML, 1),
                crearInsumo("Salsa especial", "salsas", new BigDecimal("700"), new BigDecimal("950"), UnidadMedidaEnum.ML, 1),
                crearInsumo("Batatas", "verduras", new BigDecimal("700"), new BigDecimal("950"), UnidadMedidaEnum.GR, 2),
                crearInsumo("Papas", "verduras", new BigDecimal("700"), new BigDecimal("950"), UnidadMedidaEnum.GR, 2),
                crearInsumo("Nachos", "snacks", new BigDecimal("7100"), new BigDecimal("8300"), UnidadMedidaEnum.GR, 2),
                crearInsumo("Guacamole", "salsas", new BigDecimal("4300"), new BigDecimal("5500"), UnidadMedidaEnum.ML, 1),
                crearInsumo("Huevo", "lacteos", new BigDecimal("1800"), new BigDecimal("2400"), UnidadMedidaEnum.UNIDAD, 2),
                crearInsumo("Harina 000", "panaderia", new BigDecimal("900"), new BigDecimal("1300"), UnidadMedidaEnum.GR, 1),
                crearInsumo("Manteca", "lacteos", new BigDecimal("1900"), new BigDecimal("2500"), UnidadMedidaEnum.GR, 2),
                crearInsumo("Ketchup", "salsas", new BigDecimal("1600"), new BigDecimal("2100"), UnidadMedidaEnum.GR, 1),
                crearInsumo("Pepinillos", "verduras", new BigDecimal("2500"), new BigDecimal("3200"), UnidadMedidaEnum.GR, 2),
                crearInsumo("Pan rallado", "panaderia", new BigDecimal("1100"), new BigDecimal("1500"), UnidadMedidaEnum.GR, 1),
                crearInsumo("Ajo en polvo", "condimentos", new BigDecimal("1700"), new BigDecimal("2300"), UnidadMedidaEnum.GR, 0),
                crearInsumo("Pimienta", "condimentos", new BigDecimal("2000"), new BigDecimal("2700"), UnidadMedidaEnum.GR, 0),
                crearInsumo("Aceite vegetal", "condimentos", new BigDecimal("1700"), new BigDecimal("2200"), UnidadMedidaEnum.ML, 0),
                crearInsumo("Jalapeños", "verduras", new BigDecimal("2600"), new BigDecimal("3300"), UnidadMedidaEnum.GR, 1),
                crearInsumo("Ciboulette", "condimentos", new BigDecimal("900"), new BigDecimal("1200"), UnidadMedidaEnum.GR, 0),
                crearInsumo("Pan tipo brioche", "panaderia", new BigDecimal("1800"), new BigDecimal("2300"), UnidadMedidaEnum.GR, 2),
                crearInsumo("Salsa cheddar", "salsas", new BigDecimal("1900"), new BigDecimal("2600"), UnidadMedidaEnum.GR, 1),
                crearInsumo("Curry", "condimentos", new BigDecimal("2300"), new BigDecimal("2900"), UnidadMedidaEnum.GR, 0),
                crearInsumo("Zanahoria", "verduras", new BigDecimal("850"), new BigDecimal("1100"), UnidadMedidaEnum.GR, 1),
                crearInsumo("Pickles", "verduras", new BigDecimal("2100"), new BigDecimal("2800"), UnidadMedidaEnum.GR, 1),
                crearInsumo("Panceta ahumada", "carnes", new BigDecimal("3400"), new BigDecimal("4700"), UnidadMedidaEnum.GR, 3),
                crearInsumo("Chimichurri", "salsas", new BigDecimal("1500"), new BigDecimal("2000"), UnidadMedidaEnum.GR, 1),
                crearInsumo("Perejil fresco", "verduras", new BigDecimal("900"), new BigDecimal("1200"), UnidadMedidaEnum.GR, 1)
        ));

        articuloInsumoRepository.saveAll(insumos);
    }

    private ArticuloInsumoEntity crearInsumo(String nombre, String categoria, BigDecimal costo, BigDecimal venta,
                                             UnidadMedidaEnum unidad, int tiempoMinutos) {
        return ArticuloInsumoEntity.builder()
                .denominacion(nombre)
                .precioCosto(costo)
                .precioVenta(venta)
                .unidadMedidaEnum(unidad)
                .stockActual(4000.0)
                .stockMaximo(10000.0)
                .stockMinimo(1000.0)
                .tiempoEstimadoMinutos(tiempoMinutos)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion(categoria))
                .sucursal(sucursalRepository.findById(1L).orElseThrow())
                .build();
    }
}
