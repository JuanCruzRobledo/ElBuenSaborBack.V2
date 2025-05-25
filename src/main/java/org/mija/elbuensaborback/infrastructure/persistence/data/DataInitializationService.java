package org.mija.elbuensaborback.infrastructure.persistence.data;

import jakarta.persistence.EntityNotFoundException;
import org.mija.elbuensaborback.domain.enums.PermissionEnum;
import org.mija.elbuensaborback.domain.enums.RolEnum;
import org.mija.elbuensaborback.domain.enums.UnidadMedidaEnum;
import org.mija.elbuensaborback.infrastructure.persistence.entity.*;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DataInitializationService {


    private final PaisJpaRepository paisRepository;
    private final ProvinciaJpaRepository provinciaRepository;
    private final LocalidadJpaRepository localidadRepository;
    private final EmpresaJpaRepository empresaRepository;
    private final CategoriaJpaRepository categoriaRepository;
    private final ArticuloInsumoJpaRepository articuloInsumoRepository;
    private final ArticuloManufacturadoJpaRepository articuloManufacturadoRepository;
    private final RoleJpaRepository roleRepository;
    private final PersonaJpaRepository personaRepository;
    private final SucursalJpaRepository sucursalJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final PermissionJpaRepository permissionRepository;

    public DataInitializationService(PaisJpaRepository paisRepository, ProvinciaJpaRepository provinciaRepository, LocalidadJpaRepository localidadRepository, EmpresaJpaRepository empresaRepository, CategoriaJpaRepository categoriaRepository, ArticuloInsumoJpaRepository articuloInsumoRepository, ArticuloManufacturadoJpaRepository articuloManufacturadoRepository, RoleJpaRepository roleRepository, PersonaJpaRepository personaRepository, SucursalJpaRepository sucursalJpaRepository, PasswordEncoder passwordEncoder, PermissionJpaRepository permissionRepository) {
        this.paisRepository = paisRepository;
        this.provinciaRepository = provinciaRepository;
        this.localidadRepository = localidadRepository;
        this.empresaRepository = empresaRepository;
        this.categoriaRepository = categoriaRepository;
        this.articuloInsumoRepository = articuloInsumoRepository;
        this.articuloManufacturadoRepository = articuloManufacturadoRepository;
        this.roleRepository = roleRepository;
        this.personaRepository = personaRepository;
        this.sucursalJpaRepository = sucursalJpaRepository;
        this.passwordEncoder = passwordEncoder;
        this.permissionRepository = permissionRepository;
    }

    public void initPaisesProvinciasLocalidades() {
        // Paises
        PaisEntity argentina = paisRepository.save(PaisEntity.builder().nombre("Argentina").build());
        PaisEntity chile = paisRepository.save(PaisEntity.builder().nombre("Chile").build());

        // Provincias
        ProvinciaEntity mendoza = provinciaRepository.save(
                ProvinciaEntity.builder().nombre("Mendoza").pais(argentina).build()
        );

        // Localidades
        LocalidadEntity godoyCruz = LocalidadEntity.builder().nombre("Godoy Cruz").provincia(mendoza).build();
        LocalidadEntity lujanDeCuyo = LocalidadEntity.builder().nombre("Luján de Cuyo").provincia(mendoza).build();
        LocalidadEntity maipu = LocalidadEntity.builder().nombre("Maipú").provincia(mendoza).build();
        LocalidadEntity lasHeras = LocalidadEntity.builder().nombre("Las Heras").provincia(mendoza).build();

        localidadRepository.saveAll(List.of(godoyCruz, lujanDeCuyo, maipu, lasHeras));
    }

    public void initEmpresaYSucursal() {
        LocalidadEntity lasHeras = localidadRepository.findByNombre("Las Heras");

        SucursalEntity sucursal = SucursalEntity.builder()
                .nombre("PanchosSupremos Las Heras")
                .horarioApertura(LocalTime.of(20, 0))
                .horarioCierre(LocalTime.of(1, 0))
                .domicilio(DomicilioEntity.builder()
                        .calle("Av.Pepe").numero(123).codigoPostal("5523").localidad(lasHeras)
                        .build())
                .build();

        EmpresaEntity empresa = EmpresaEntity.builder()
                .cuil(232323232)
                .nombre("PanchosSupremos")
                .razonSocial("PanchosSupremos")
                .listaSucursal(List.of(sucursal))
                .build();

        sucursal.setEmpresa(empresa);

        empresaRepository.save(empresa);
    }

    public void initCategorias() {
        CategoriaEntity manufacturado = CategoriaEntity.builder().denominacion("Manufacturado").build();
        CategoriaEntity insumo = CategoriaEntity.builder().denominacion("Insumo").build();

        CategoriaEntity snacks = CategoriaEntity.builder().denominacion("Snacks").categoriaPadre(manufacturado).build();
        CategoriaEntity comidas = CategoriaEntity.builder().denominacion("Comidas").categoriaPadre(manufacturado).build();
        CategoriaEntity pizzas = CategoriaEntity.builder().denominacion("Pizzas").categoriaPadre(comidas).build();
        CategoriaEntity hamburguesas = CategoriaEntity.builder().denominacion("Hamburguesas").categoriaPadre(comidas).build();
        CategoriaEntity hamburguesasVeganas = CategoriaEntity.builder().denominacion("Hamburguesas Veganas").categoriaPadre(hamburguesas).build();
        CategoriaEntity empanadas = CategoriaEntity.builder().denominacion("Empanadas").categoriaPadre(comidas).build();
        CategoriaEntity pastas = CategoriaEntity.builder().denominacion("Pastas").categoriaPadre(comidas).build();

        CategoriaEntity postres = CategoriaEntity.builder().denominacion("Postres").categoriaPadre(manufacturado).build();
        CategoriaEntity helados = CategoriaEntity.builder().denominacion("Helados").categoriaPadre(postres).build();
        CategoriaEntity tortas = CategoriaEntity.builder().denominacion("Tortas").categoriaPadre(postres).build();
        CategoriaEntity flanes = CategoriaEntity.builder().denominacion("Flanes y Budines").categoriaPadre(postres).build();

        CategoriaEntity combos = CategoriaEntity.builder().denominacion("Combos").categoriaPadre(manufacturado).build();
        CategoriaEntity comboFamiliar = CategoriaEntity.builder().denominacion("Combo Familiar").categoriaPadre(combos).build();
        CategoriaEntity comboIndividual = CategoriaEntity.builder().denominacion("Combo Individual").categoriaPadre(combos).build();

        CategoriaEntity ensaladas = CategoriaEntity.builder().denominacion("Ensaladas").categoriaPadre(manufacturado).build();
        CategoriaEntity ensaladasClasicas = CategoriaEntity.builder().denominacion("Clásicas").categoriaPadre(ensaladas).build();
        CategoriaEntity ensaladasEspeciales = CategoriaEntity.builder().denominacion("Especiales").categoriaPadre(ensaladas).build();

        CategoriaEntity salsas = CategoriaEntity.builder().denominacion("Salsas").categoriaPadre(insumo).build();
        CategoriaEntity panaderia = CategoriaEntity.builder().denominacion("Panadería").categoriaPadre(insumo).build();
        CategoriaEntity lacteos = CategoriaEntity.builder().denominacion("Lácteos").categoriaPadre(insumo).build();
        CategoriaEntity verduras = CategoriaEntity.builder().denominacion("Verduras").categoriaPadre(insumo).build();
        CategoriaEntity carnes = CategoriaEntity.builder().denominacion("Carnes").categoriaPadre(insumo).build();
        CategoriaEntity condimentos = CategoriaEntity.builder().denominacion("Condimentos").categoriaPadre(insumo).build();

        CategoriaEntity bebidas = CategoriaEntity.builder().denominacion("Bebidas").categoriaPadre(insumo).build();
        CategoriaEntity gaseosas = CategoriaEntity.builder().denominacion("Gaseosas").categoriaPadre(bebidas).build();
        CategoriaEntity aguas = CategoriaEntity.builder().denominacion("Aguas").categoriaPadre(bebidas).build();
        CategoriaEntity cervezas = CategoriaEntity.builder().denominacion("Cervezas").categoriaPadre(bebidas).build();
        CategoriaEntity jugos = CategoriaEntity.builder().denominacion("Jugos").categoriaPadre(bebidas).build();

        categoriaRepository.saveAll(List.of(
                manufacturado, insumo,
                comidas, pizzas, hamburguesas, hamburguesasVeganas, empanadas, pastas,
                postres, helados, tortas, flanes,
                combos, comboFamiliar, comboIndividual,
                ensaladas, ensaladasClasicas, ensaladasEspeciales,
                salsas, panaderia, lacteos, verduras, carnes, condimentos,
                bebidas, gaseosas, aguas, cervezas, jugos,snacks
        ));
    }

    public void initArticulosInsumos() {

        SucursalEntity sucursalEntity1 = sucursalJpaRepository.findById(1L).orElseThrow(()->new EntityNotFoundException("No se encontro la sucursal"));

        //ARTICULOS INSUMOS
        ArticuloInsumoEntity panHamburguesa = ArticuloInsumoEntity.builder()
                .denominacion("Pan de Hamburguesa")
                .precioCompra(new BigDecimal("100"))
                .precioVenta(new BigDecimal("0"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("panaderia"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity medallonCarne = ArticuloInsumoEntity.builder()
                .denominacion("Medallón de Carne")
                .precioCompra(new BigDecimal("350"))
                .precioVenta(new BigDecimal("0"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("carnes"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity quesoCheddar = ArticuloInsumoEntity.builder()
                .denominacion("Queso Cheddar")
                .precioCompra(new BigDecimal("200"))
                .precioVenta(new BigDecimal("0"))
                .tiempoEstimadoMinutos(20)
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("lacteos"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity lechuga = ArticuloInsumoEntity.builder()
                .denominacion("Lechuga")
                .precioCompra(new BigDecimal("50"))
                .precioVenta(new BigDecimal("0"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("verduras"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity tomate = ArticuloInsumoEntity.builder()
                .denominacion("Tomate")
                .precioCompra(new BigDecimal("70"))
                .precioVenta(new BigDecimal("0"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("verduras"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity mayonesa = ArticuloInsumoEntity.builder()
                .denominacion("Mayonesa")
                .precioCompra(new BigDecimal("60"))
                .precioVenta(new BigDecimal("0"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("salsas"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity mostaza = ArticuloInsumoEntity.builder()
                .denominacion("Mostaza")
                .precioCompra(new BigDecimal("55"))
                .precioVenta(new BigDecimal("0"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("salsas"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity sal = ArticuloInsumoEntity.builder()
                .denominacion("Sal")
                .precioCompra(new BigDecimal("20"))
                .precioVenta(new BigDecimal("0"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("condimentos"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity bacon = ArticuloInsumoEntity.builder()
                .denominacion("Bacon")
                .precioCompra(new BigDecimal("300"))
                .precioVenta(new BigDecimal("0"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("carnes"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity panArtesanal = ArticuloInsumoEntity.builder()
                .denominacion("Pan artesanal")
                .precioCompra(new BigDecimal("120"))
                .precioVenta(new BigDecimal("0"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("panaderia"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity cebollaCaramelizada = ArticuloInsumoEntity.builder()
                .denominacion("Cebolla caramelizada")
                .precioCompra(new BigDecimal("80"))
                .precioVenta(new BigDecimal("0"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("verduras"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity queso = ArticuloInsumoEntity.builder()
                .denominacion("Queso")
                .precioCompra(new BigDecimal("200"))
                .precioVenta(new BigDecimal("0"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("lacteos"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity salsaBarbacoa = ArticuloInsumoEntity.builder()
                .denominacion("Salsa barbacoa")
                .precioCompra(new BigDecimal("100"))
                .precioVenta(new BigDecimal("0"))
                .unidadMedidaEnum(UnidadMedidaEnum.ML)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("salsas"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity cebollaFresca = ArticuloInsumoEntity.builder()
                .denominacion("Cebolla fresca")
                .precioCompra(new BigDecimal("60"))
                .precioVenta(new BigDecimal("0"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("verduras"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity cebollaCrujiente = ArticuloInsumoEntity.builder()
                .denominacion("Cebolla crujiente")
                .precioCompra(new BigDecimal("90"))
                .precioVenta(new BigDecimal("0"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("verduras"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity salsaEspecial = ArticuloInsumoEntity.builder()
                .denominacion("Salsa especial")
                .precioCompra(new BigDecimal("120"))
                .precioVenta(new BigDecimal("0"))
                .unidadMedidaEnum(UnidadMedidaEnum.ML)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("salsas"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity batatas = ArticuloInsumoEntity.builder()
                .denominacion("Batatas")
                .precioCompra(new BigDecimal("70"))
                .precioVenta(new BigDecimal("0"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("verduras"))
                .sucursal(sucursalEntity1)
                .build();

        ArticuloInsumoEntity papas = ArticuloInsumoEntity.builder()
                .denominacion("Papas")
                .precioCompra(new BigDecimal("60"))
                .precioVenta(new BigDecimal("0"))
                .unidadMedidaEnum(UnidadMedidaEnum.GR)
                .stockActual(4000.00)
                .stockMaximo(10000.00)
                .stockMinimo(1000.00)
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
                .esParaPreparar(true)
                .esVendible(false)
                .productoActivo(true)
                .categoria(categoriaRepository.findByDenominacion("salsas"))
                .sucursal(sucursalEntity1)
                .build();


        articuloInsumoRepository.saveAll(List.of(bacon, panArtesanal, cebollaCaramelizada, queso,
                salsaBarbacoa, cebollaFresca, cebollaCrujiente,
                salsaEspecial, batatas, papas, nachos, guacamole,
                panHamburguesa, medallonCarne, quesoCheddar,
                lechuga, tomate, mayonesa, mostaza, sal
        ));
    }

    public void initRoleAndPermission(){
        /* CREATE PERMISSIONS */
        PermissionEntity permissionEntity1 = PermissionEntity.builder()
                .permissionEnum(PermissionEnum.CREATE)
                .build();

        PermissionEntity permissionEntity2 = PermissionEntity.builder()
                .permissionEnum(PermissionEnum.DELETE)
                .build();

        PermissionEntity permissionEntity3 = PermissionEntity.builder()
                .permissionEnum(PermissionEnum.UPDATE)
                .build();

        permissionRepository.saveAll(List.of(permissionEntity1, permissionEntity2, permissionEntity3));

        /* CREATE ROLES */
        RoleEntity roleAdmin = RoleEntity.builder()
                .rolEnum(RolEnum.ADMIN)
                .permisos(new HashSet<>(Set.of(permissionEntity1, permissionEntity2, permissionEntity3))) // Usa new HashSet<>()
                .build();

        RoleEntity roleCliente = RoleEntity.builder()
                .rolEnum(RolEnum.CLIENTE)
                .permisos(new HashSet<>(Set.of(permissionEntity1, permissionEntity2))) // Usa new HashSet<>()
                .build();

        RoleEntity roleEmpleado = RoleEntity.builder()
                .rolEnum(RolEnum.EMPLEADO)
                .permisos(new HashSet<>(Set.of(permissionEntity1))) // Usa new HashSet<>()
                .build();

        roleRepository.saveAll(List.of(roleAdmin, roleCliente, roleEmpleado));

    }

    public void initClientWithUsers(){

        /* CREATE USERS */
        UsuarioEntity userJuan = UsuarioEntity.builder()
                .email("juan@gmail.com")
                .password(passwordEncoder.encode("112233"))
                .disabled(false)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .rol(roleRepository.findByRolEnum(RolEnum.ADMIN).orElseThrow(()-> new EntityNotFoundException("No se pudo encontrar el Rol")))
                .build();

        UsuarioEntity userAmbar= UsuarioEntity.builder()
                .email("ambar@gmail.com")
                .password(passwordEncoder.encode("112233"))
                .disabled(false)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .rol(roleRepository.findByRolEnum(RolEnum.CLIENTE).orElseThrow(()-> new EntityNotFoundException("No se pudo encontrar el Rol")))
                .build();

        UsuarioEntity userIsabella = UsuarioEntity.builder()
                .email("isabella@gmail.com")
                .password(passwordEncoder.encode("112233"))
                .disabled(false)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .rol(roleRepository.findByRolEnum(RolEnum.EMPLEADO).orElseThrow(()-> new EntityNotFoundException("No se pudo encontrar el Rol")))
                .build();

        UsuarioEntity userMaiten = UsuarioEntity.builder()
                .email("maiten@gmail.com")
                .password(passwordEncoder.encode("112233"))
                .disabled(false)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .rol(roleRepository.findByRolEnum(RolEnum.EMPLEADO).orElseThrow(()-> new EntityNotFoundException("No se pudo encontrar el Rol")))
                .build();

        /* CREATE CLIENTES */

        DomicilioEntity domicilio = DomicilioEntity.builder()
                .numero(123)
                .calle("Av. San Martin")
                .codigoPostal("5555")
                .localidad(localidadRepository.findByNombre("Godoy Cruz"))
                .build();

        ClienteEntity cliente1 = ClienteEntity.builder()
                .nombre("Ambar")
                .apellido("Gonzalez")
                .telefono("123456789")
                .usuario(userAmbar)
                .domicilio(List.of(domicilio)) // opcional, si no tenés domicilios
                .listaPedido(new ArrayList<>()) // opcional, si no hay pedidos
                .build();

        ClienteEntity cliente2 = ClienteEntity.builder()
                .nombre("Juan")
                .apellido("Perez")
                .telefono("987654321")
                .usuario(userJuan)
                .domicilio(new ArrayList<>())
                .listaPedido(new ArrayList<>())
                .build();

        /* CREATE EMPLEADOS */

        SucursalEntity sucursal = sucursalJpaRepository.findById(1L).orElseThrow(()-> new EntityNotFoundException("No se encontro la sucursal"));

        EmpleadoEntity empleado1 = EmpleadoEntity.builder()
                .nombre("Isabella")
                .apellido("Lopez")
                .telefono("555111222")
                .usuario(userIsabella)
                .sucursal(sucursal) // asigná una sucursal si tenés
                .build();

        EmpleadoEntity empleado2 = EmpleadoEntity.builder()
                .nombre("Maiten")
                .apellido("Fernandez")
                .telefono("444333222")
                .usuario(userMaiten)
                .sucursal(sucursal) // asigná una sucursal si tenés
                .build();

        /* GUARDAR TODO CON UN SOLO saveAll */
        personaRepository.saveAll(List.of(cliente1, cliente2, empleado1, empleado2));
    }
}