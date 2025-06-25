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
                crearInsumo("Pan de Hamburguesa Comun", "Panaderia", new BigDecimal("1200"), new BigDecimal("1600"), UnidadMedidaEnum.GR, 3),
                crearInsumo("Medallon de Carne", "Carnes", new BigDecimal("3500"), new BigDecimal("4800"), UnidadMedidaEnum.GR, 5),
                crearInsumo("Medallon de Chorizo", "Carnes", new BigDecimal("3500"), new BigDecimal("4800"), UnidadMedidaEnum.GR, 5),
                crearInsumo("Medallon de Garbanzos", "Legumbres", new BigDecimal("3500"), new BigDecimal("4800"), UnidadMedidaEnum.GR, 5),
                crearInsumo("Queso Cheddar", "Lacteos", new BigDecimal("2200"), new BigDecimal("3000"), UnidadMedidaEnum.GR, 2),
                crearInsumo("Lechuga", "Verduras", new BigDecimal("800"), new BigDecimal("1000"), UnidadMedidaEnum.GR, 1),
                crearInsumo("Tomate", "Verduras", new BigDecimal("900"), new BigDecimal("1200"), UnidadMedidaEnum.GR, 1),
                crearInsumo("Mayonesa", "Salsas", new BigDecimal("1500"), new BigDecimal("2000"), UnidadMedidaEnum.GR, 1),
                crearInsumo("Mostaza", "Salsas", new BigDecimal("1400"), new BigDecimal("1900"), UnidadMedidaEnum.GR, 1),
                crearInsumo("Sal", "Condimentos", new BigDecimal("500"), new BigDecimal("800"), UnidadMedidaEnum.GR, 0),
                crearInsumo("Bacon", "Carnes", new BigDecimal("3200"), new BigDecimal("4500"), UnidadMedidaEnum.GR, 3),
                crearInsumo("Pan artesanal", "Panaderia", new BigDecimal("1500"), new BigDecimal("2000"), UnidadMedidaEnum.GR, 1),
                crearInsumo("Cebolla", "Verduras", new BigDecimal("1000"), new BigDecimal("1400"), UnidadMedidaEnum.GR, 5),
                crearInsumo("Queso Tibo", "Lacteos", new BigDecimal("2100"), new BigDecimal("2800"), UnidadMedidaEnum.GR, 2),
                crearInsumo("Queso provolone", "Lacteos", new BigDecimal("2100"), new BigDecimal("2800"), UnidadMedidaEnum.GR, 2),
                crearInsumo("Queso vegano", "Lacteos", new BigDecimal("2100"), new BigDecimal("2800"), UnidadMedidaEnum.GR, 2),
                crearInsumo("Salsa barbacoa", "Salsas", new BigDecimal("1300"), new BigDecimal("1800"), UnidadMedidaEnum.ML, 1),
                crearInsumo("Salsa especial", "Salsas", new BigDecimal("700"), new BigDecimal("950"), UnidadMedidaEnum.ML, 1),
                crearInsumo("Batatas", "Verduras", new BigDecimal("700"), new BigDecimal("950"), UnidadMedidaEnum.GR, 2),
                crearInsumo("Papas", "Verduras", new BigDecimal("700"), new BigDecimal("950"), UnidadMedidaEnum.GR, 2),
                crearInsumo("Nachos", "Snacks", new BigDecimal("7100"), new BigDecimal("8300"), UnidadMedidaEnum.GR, 2),
                crearInsumo("Guacamole", "Salsas", new BigDecimal("4300"), new BigDecimal("5500"), UnidadMedidaEnum.ML, 1),
                crearInsumo("Huevo", "Origen Animal", new BigDecimal("1800"), new BigDecimal("2400"), UnidadMedidaEnum.UNIDAD, 2),
                crearInsumo("Harina 000", "Panaderia", new BigDecimal("900"), new BigDecimal("1300"), UnidadMedidaEnum.GR, 1),
                crearInsumo("Manteca", "Lacteos", new BigDecimal("1900"), new BigDecimal("2500"), UnidadMedidaEnum.GR, 2),
                crearInsumo("Ketchup", "Salsas", new BigDecimal("1600"), new BigDecimal("2100"), UnidadMedidaEnum.GR, 1),
                crearInsumo("Pepinillos", "Verduras", new BigDecimal("2500"), new BigDecimal("3200"), UnidadMedidaEnum.GR, 2),
                crearInsumo("Champiñones", "Hongos", new BigDecimal("2500"), new BigDecimal("3200"), UnidadMedidaEnum.GR, 2),
                crearInsumo("Pan rallado", "Panaderia", new BigDecimal("1100"), new BigDecimal("1500"), UnidadMedidaEnum.GR, 1),
                crearInsumo("Ajo en polvo", "Condimentos", new BigDecimal("1700"), new BigDecimal("2300"), UnidadMedidaEnum.GR, 0),
                crearInsumo("Pimienta", "Condimentos", new BigDecimal("2000"), new BigDecimal("2700"), UnidadMedidaEnum.GR, 0),
                crearInsumo("Aceite", "Condimentos", new BigDecimal("1700"), new BigDecimal("2200"), UnidadMedidaEnum.ML, 0),
                crearInsumo("Jalapeños", "Verduras", new BigDecimal("2600"), new BigDecimal("3300"), UnidadMedidaEnum.GR, 1),
                crearInsumo("Pollo Deshuesado", "Carnes", new BigDecimal("900"), new BigDecimal("1200"), UnidadMedidaEnum.GR, 0),
                crearInsumo("Pan con Sésamo", "Panaderia", new BigDecimal("1800"), new BigDecimal("2300"), UnidadMedidaEnum.GR, 2),
                crearInsumo("Curry", "Condimentos", new BigDecimal("2300"), new BigDecimal("2900"), UnidadMedidaEnum.GR, 0),
                crearInsumo("Mayonesa de Zanahoria", "Salsas", new BigDecimal("850"), new BigDecimal("1100"), UnidadMedidaEnum.GR, 1),
                crearInsumo("Salsa Picante", "Salsas", new BigDecimal("700"), new BigDecimal("900"), UnidadMedidaEnum.GR, 1),
                crearInsumo("Chimichurri", "Salsas", new BigDecimal("1500"), new BigDecimal("2000"), UnidadMedidaEnum.GR, 1),
                crearInsumo("Perejil fresco", "Verduras", new BigDecimal("900"), new BigDecimal("1200"), UnidadMedidaEnum.GR, 1)
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
