package org.mija.elbuensaborback.infrastructure.persistence.data;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.infrastructure.persistence.entity.CategoriaEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.CategoriaJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaData {

    private final CategoriaJpaRepository categoriaRepository;

    public void initCategorias() {
        CategoriaEntity manufacturado = CategoriaEntity.builder().denominacion("Manufacturado").build();
        CategoriaEntity insumo = CategoriaEntity.builder().denominacion("Insumo").build();
//        CategoriaEntity combos = CategoriaEntity.builder().denominacion("Combos").build();

        CategoriaEntity sides = CategoriaEntity.builder().denominacion("Sides").categoriaPadre(manufacturado).build();
        CategoriaEntity hamburguesas = CategoriaEntity.builder().denominacion("Hamburguesas").categoriaPadre(manufacturado).build();
//        CategoriaEntity hamburguesasVeganas = CategoriaEntity.builder().denominacion("Hamburguesas Veganas").categoriaPadre(hamburguesas).build();
//        CategoriaEntity comidas = CategoriaEntity.builder().denominacion("Comidas").categoriaPadre(manufacturado).build();
//        CategoriaEntity pizzas = CategoriaEntity.builder().denominacion("Pizzas").categoriaPadre(comidas).build();
//        CategoriaEntity empanadas = CategoriaEntity.builder().denominacion("Empanadas").categoriaPadre(comidas).build();
//        CategoriaEntity pastas = CategoriaEntity.builder().denominacion("Pastas").categoriaPadre(comidas).build();

//        CategoriaEntity postres = CategoriaEntity.builder().denominacion("Postres").categoriaPadre(manufacturado).build();
//        CategoriaEntity helados = CategoriaEntity.builder().denominacion("Helados").categoriaPadre(postres).build();
//        CategoriaEntity tortas = CategoriaEntity.builder().denominacion("Tortas").categoriaPadre(postres).build();
//        CategoriaEntity flanes = CategoriaEntity.builder().denominacion("Flanes y Budines").categoriaPadre(postres).build();

          CategoriaEntity combos = CategoriaEntity.builder().denominacion("Combos").categoriaPadre(manufacturado).build();
          CategoriaEntity comboFamiliar = CategoriaEntity.builder().denominacion("Combo Familiar").categoriaPadre(combos).build();
          CategoriaEntity comboIndividual = CategoriaEntity.builder().denominacion("Combo Individual").categoriaPadre(combos).build();

//        CategoriaEntity ensaladas = CategoriaEntity.builder().denominacion("Ensaladas").categoriaPadre(manufacturado).build();
//        CategoriaEntity ensaladasClasicas = CategoriaEntity.builder().denominacion("Clásicas").categoriaPadre(ensaladas).build();
//        CategoriaEntity ensaladasEspeciales = CategoriaEntity.builder().denominacion("Especiales").categoriaPadre(ensaladas).build();

        CategoriaEntity salsas = CategoriaEntity.builder().denominacion("Salsas").categoriaPadre(insumo).build();
        CategoriaEntity panaderia = CategoriaEntity.builder().denominacion("Panaderia").categoriaPadre(insumo).build();
        CategoriaEntity lacteos = CategoriaEntity.builder().denominacion("Lacteos").categoriaPadre(insumo).build();
        CategoriaEntity verduras = CategoriaEntity.builder().denominacion("Verduras").categoriaPadre(insumo).build();
        CategoriaEntity legumbres = CategoriaEntity.builder().denominacion("Legumbres").categoriaPadre(insumo).build();
        CategoriaEntity hongos = CategoriaEntity.builder().denominacion("Hongos").categoriaPadre(insumo).build();
        CategoriaEntity origenAnimal = CategoriaEntity.builder().denominacion("Origen Animal").categoriaPadre(insumo).build();
        CategoriaEntity carnes = CategoriaEntity.builder().denominacion("Carnes").categoriaPadre(origenAnimal).build();
        CategoriaEntity condimentos = CategoriaEntity.builder().denominacion("Condimentos").categoriaPadre(insumo).build();
        CategoriaEntity snacks = CategoriaEntity.builder().denominacion("Snacks").categoriaPadre(insumo).build();

        CategoriaEntity bebidas = CategoriaEntity.builder().denominacion("Bebidas").categoriaPadre(insumo).build();
        CategoriaEntity gaseosas = CategoriaEntity.builder().denominacion("Gaseosas").categoriaPadre(bebidas).build();
        CategoriaEntity aguas = CategoriaEntity.builder().denominacion("Aguas").categoriaPadre(bebidas).build();
        CategoriaEntity cervezas = CategoriaEntity.builder().denominacion("Cervezas").categoriaPadre(bebidas).build();
        CategoriaEntity jugos = CategoriaEntity.builder().denominacion("Jugos").categoriaPadre(bebidas).build();
        CategoriaEntity energizantes = CategoriaEntity.builder().denominacion("Energizantes").categoriaPadre(bebidas).build();


        categoriaRepository.saveAll(List.of(
                manufacturado, insumo,
                hamburguesas,
                salsas, panaderia, lacteos, verduras, legumbres, hongos, carnes, condimentos,snacks,
                bebidas, gaseosas, aguas, cervezas, jugos, energizantes,sides,
                combos, comboIndividual, comboFamiliar
        ));
    }
}
