package org.mija.elbuensaborback.infrastructure.persistence.data;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class DataInitializationService {

    private final ArticuloManufacturadoData manufacturadoData;
    private final ArticuloInsumoData articuloInsumoData;
    private final CategoriaData categoriaData;
    private final UbicacionesData ubicacionesData;
    private final EmpresaSucursalData empresaSucursalData;
    private final RolesPermisosData rolesPermisosData;
    private final ClienteData clienteData;
    private final EmpleadoData empleadoData;
    private final ArticuloPromocionData articuloPromocionData;
    private final ArticuloInsumoVendibleData articuloInsumoVendibleData;


    public void init(){
        ubicacionesData.initPaisesProvinciasLocalidades();
        empresaSucursalData.initEmpresaYSucursal();
        categoriaData.initCategorias();
        articuloInsumoData.initArticulosInsumos();
        articuloInsumoVendibleData.initArticulosInsumosVendibles();
        rolesPermisosData.initRoleAndPermission();
        empleadoData.initEmpleadoWithUser();
        clienteData.initClientWithUsers();
        manufacturadoData.initArticulosManufacturados();
        articuloPromocionData.initPromociones();
    }
}