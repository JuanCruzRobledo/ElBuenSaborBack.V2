package org.mija.elbuensaborback.application.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.request.insumo.ArticuloActualizarStockPrecioRequest;
import org.mija.elbuensaborback.application.dto.request.insumo.ArticuloInsumoCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.insumo.ArticuloInsumoUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ArticuloInsumoBasicResponse;
import org.mija.elbuensaborback.application.dto.response.ArticuloInsumoMenuBasicResponse;
import org.mija.elbuensaborback.application.dto.response.ArticuloInsumoResponse;
import org.mija.elbuensaborback.application.mapper.ArticuloInsumoMapper;
import org.mija.elbuensaborback.application.service.contratos.ArticuloInsumoService;
import org.mija.elbuensaborback.infrastructure.persistence.entity.*;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticuloInsumoServiceImpl implements ArticuloInsumoService {

    private final CategoriaRepositoryImpl categoriaRepository;
    private final ArticuloInsumoRepositoryImpl articuloInsumoRepository;
    private final ArticuloInsumoMapper articuloInsumoMapper;
    private final ArticuloManufacturadoDetalleRepositoryImpl articuloManufacturadoDetalleRepository;
    private final PromocionDetalleRepositoryImpl promocionDetalleRepository;
    private final ArticuloManufacturadoRepositoryImpl articuloManufacturadoRepository;
    private final ArticuloPromocionRepositoryImpl articuloPromocionRepository;

    @Override
    public ArticuloInsumoResponse crearArticuloInsumo(ArticuloInsumoCreatedRequest articuloCreatedRequest) {
        CategoriaEntity categoria =  categoriaRepository.findById(articuloCreatedRequest.categoriaId()).orElseThrow(()-> new EntityNotFoundException("No se encontro la categoria"));
        ArticuloInsumoEntity insumo = articuloInsumoMapper.toEntity(articuloCreatedRequest);
        SucursalEntity sucursal = SucursalEntity.builder().id(1L).build();
        insumo.setSucursal(sucursal);
        insumo.setCategoria(categoria);

        return articuloInsumoMapper.toResponse(articuloInsumoRepository.save(insumo));
    }

    @Override
    @Transactional
    public ArticuloInsumoResponse actualizarArticuloInsumo(Long id, ArticuloInsumoUpdateRequest articuloUpdateRequest) {

        CategoriaEntity categoria = categoriaRepository.findById(articuloUpdateRequest.categoriaId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la categoría"));

        ArticuloInsumoEntity insumo = articuloInsumoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se pudo encontrar el insumo"));

        // Guardamos el estado anterior
        boolean estabaInactivo = insumo.getProductoActivo() != null && !insumo.getProductoActivo();

        // Actualizamos el insumo con el mapper
        articuloInsumoMapper.updateEntity(insumo, articuloUpdateRequest);
        insumo.setCategoria(categoria);

        // Guardamos el nuevo estado para saber si ahora está activo
        boolean estaActivo = insumo.getProductoActivo() != null && insumo.getProductoActivo();

        // Si estaba inactivo y ahora está activo, reactivar los relacionados
        if (estabaInactivo && estaActivo) {
            reactivarRelacionados(insumo);
        }

        return articuloInsumoMapper.toResponse(articuloInsumoRepository.save(insumo));
    }

    @Override
    public ArticuloInsumoResponse obtenerArticuloInsumo(Long id) {
        ArticuloInsumoEntity articuloInsumo = articuloInsumoRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("No se pudo encontrar el Insumo"));

        System.out.println("IMAGENES: " );
        articuloInsumo.getImagenesUrls().forEach(imagen -> System.out.println("IMAGEN ID: "+imagen.getId() + "IMAGEN DENOMINACION: "+ imagen.getUrl()));
        return articuloInsumoMapper.toResponse(articuloInsumo);
    }

    @Override
    @Transactional
    public void eliminarArticuloInsumo(Long id) {
        ArticuloInsumoEntity insumo = articuloInsumoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Insumo no encontrado con id: " + id));

        insumo.desactivar();
        articuloInsumoRepository.save(insumo);

        desactivarRelacionados(insumo);
    }

    @Override
    public Set<ArticuloInsumoResponse> listarArticulosInsumo() {
        List<ArticuloInsumoEntity> articuloInsumoEntities = articuloInsumoRepository.findAll();

        return articuloInsumoEntities.stream().map(articuloInsumoMapper::toResponse).collect(Collectors.toSet()) ;
    }

    @Override
    public ArticuloInsumoResponse actualizarPrecioYStock(Long id, ArticuloActualizarStockPrecioRequest request) {
        ArticuloInsumoEntity articulo = articuloInsumoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artículo no encontrado con ID: " + id));

        if (request.precioCosto() != null) {
            articulo.setPrecioCosto(request.precioCosto());
        }

        if (request.stockActual() != null) {
            articulo.setStockActual(request.stockActual());
        }

        articuloInsumoRepository.save(articulo);

        return articuloInsumoMapper.toResponse(articulo);
    }

    @Override
    public List<ArticuloInsumoBasicResponse> listarBasicArticulosInsumo() {
        return articuloInsumoRepository.basicFindAll();
    }

    public List<ArticuloInsumoMenuBasicResponse> obtenerBebidas(String denominacion) {
        CategoriaEntity categoriaPadre = categoriaRepository.findByDenominacion(denominacion);
        List<ArticuloInsumoEntity> listaDeBebidas = new ArrayList<>();

        for (CategoriaEntity categoria : categoriaPadre.getSubcategorias()) {
            List<ArticuloInsumoEntity> bebidas =  articuloInsumoRepository.findAllByCategoriaVendibles(categoria.getDenominacion());
            listaDeBebidas.addAll(bebidas);
        }


        return listaDeBebidas.stream().map(articuloInsumoMapper::menuBasicResponse).toList();
    }


    // ========================== METODOS PARA DESACTIVAR ARTICULOS ====================

    private void reactivarRelacionados(ArticuloInsumoEntity insumo) {
        // 1. Buscar manufacturados que usan este insumo
        List<ArticuloManufacturadoEntity> manufacturados =
                articuloManufacturadoDetalleRepository.findByArticuloInsumo(insumo)
                        .stream()
                        .map(ArticuloManufacturadoDetalleEntity::getArticuloManufacturado)
                        .distinct()
                        .toList();

        Set<ArticuloEntity> articulosReactivados = new HashSet<>();

        for (ArticuloManufacturadoEntity manufacturado : manufacturados) {
            boolean todosInsumosActivos = manufacturado.getArticuloManufacturadoDetalle()
                    .stream()
                    .map(ArticuloManufacturadoDetalleEntity::getArticuloInsumo)
                    .allMatch(i -> Boolean.TRUE.equals(i.getProductoActivo()));

            if (todosInsumosActivos) {
                manufacturado.activar();
                articuloManufacturadoRepository.save(manufacturado);
                articulosReactivados.add(manufacturado);
            }
        }

        // 2. Agregamos el insumo reactivado
        articulosReactivados.add(insumo);

        // 3. Buscar promociones que usan los artículos reactivados
        List<ArticuloPromocionEntity> promociones =
                promocionDetalleRepository.findByArticuloIn(articulosReactivados)
                        .stream()
                        .map(PromocionDetalleEntity::getArticuloPromocion)
                        .distinct()
                        .toList();

        for (ArticuloPromocionEntity promo : promociones) {
            boolean todosArticulosActivos = promo.getPromocionDetalle()
                    .stream()
                    .map(PromocionDetalleEntity::getArticulo)
                    .allMatch(a -> Boolean.TRUE.equals(a.getProductoActivo()));

            if (todosArticulosActivos) {
                promo.activar();
                articuloPromocionRepository.save(promo);
            }
        }
    }

    private void desactivarRelacionados(ArticuloInsumoEntity insumo) {
        // 1. Buscar manufacturados que usen este insumo
        List<ArticuloManufacturadoEntity> manufacturados =
                articuloManufacturadoDetalleRepository.findByArticuloInsumo(insumo)
                        .stream()
                        .map(ArticuloManufacturadoDetalleEntity::getArticuloManufacturado)
                        .distinct()
                        .toList();

        // 2. Desactivar manufacturados
        for (ArticuloManufacturadoEntity manufacturado : manufacturados) {
            manufacturado.desactivar();
            articuloManufacturadoRepository.save(manufacturado);
        }

        // 3. Agrupar todos los artículos afectados (insumo + manufacturados)
        Set<ArticuloEntity> afectados = new HashSet<>();
        afectados.add(insumo);
        afectados.addAll(manufacturados);

        // 4. Buscar promociones que usan cualquiera de esos artículos
        List<ArticuloPromocionEntity> promociones =
                promocionDetalleRepository.findByArticuloIn(afectados)
                        .stream()
                        .map(PromocionDetalleEntity::getArticuloPromocion)
                        .distinct()
                        .toList();

        // 5. Desactivar promociones
        for (ArticuloPromocionEntity promo : promociones) {
            promo.desactivar();
            articuloPromocionRepository.save(promo);
        }
    }

}
