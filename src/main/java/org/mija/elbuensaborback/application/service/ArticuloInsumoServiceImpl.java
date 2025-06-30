package org.mija.elbuensaborback.application.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.request.insumo.ArticuloActualizarStockPrecioRequest;
import org.mija.elbuensaborback.application.dto.request.insumo.ArticuloInsumoCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.insumo.ArticuloInsumoUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ArticuloInsumoBasicResponse;
import org.mija.elbuensaborback.application.dto.response.ArticuloInsumoMenuBasicResponse;
import org.mija.elbuensaborback.application.dto.response.ArticuloInsumoResponse;
import org.mija.elbuensaborback.application.helpers.ArticuloEstadoService;
import org.mija.elbuensaborback.application.mapper.ArticuloInsumoMapper;
import org.mija.elbuensaborback.application.service.contratos.ArticuloInsumoService;
import org.mija.elbuensaborback.infrastructure.persistence.entity.*;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    private final ArticuloEstadoService articuloEstadoService;
    private final ArticuloManufacturadoDetalleRepositoryImpl articuloManufacturadoDetalleRepository;
    private final PromocionDetalleRepositoryImpl promocionDetalleRepository;

    @Override
    public ArticuloInsumoResponse crearArticuloInsumo(ArticuloInsumoCreatedRequest articuloCreatedRequest) {
        CategoriaEntity categoria =  categoriaRepository.findById(articuloCreatedRequest.categoriaId()).orElseThrow(()-> new EntityNotFoundException("No se encontro la categoria"));
        ArticuloInsumoEntity insumo = articuloInsumoMapper.toEntity(articuloCreatedRequest);
        SucursalEntity sucursal = SucursalEntity.builder().id(1L).build();
        insumo.setSucursal(sucursal);
        insumo.setCategoria(categoria);
        insumo.calcularPrecioVenta(articuloCreatedRequest.precioVenta());

        return articuloInsumoMapper.toResponse(articuloInsumoRepository.save(insumo));
    }

    @Override
    @Transactional
    public ArticuloInsumoResponse actualizarArticuloInsumo(Long id, ArticuloInsumoUpdateRequest articuloUpdateRequest) {

        CategoriaEntity categoria = categoriaRepository.findById(articuloUpdateRequest.categoriaId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la categoría"));

        ArticuloInsumoEntity insumo = articuloInsumoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se pudo encontrar el insumo"));

        // Guardar estado anterior
        boolean estabaActivo = Boolean.TRUE.equals(insumo.getProductoActivo());

        // Mapear cambios
        articuloInsumoMapper.updateEntity(insumo, articuloUpdateRequest);
        insumo.setCategoria(categoria);

        // Calcular precio venta del insumo
        BigDecimal precioVentaManual = articuloUpdateRequest.precioVenta();
        insumo.calcularPrecioVenta(precioVentaManual);

        // Actualizar fabricados y promociones relacionados
        actualizarFabricadosYPromociones(insumo);

        // Guardar nuevo estado
        boolean estaActivoAhora = Boolean.TRUE.equals(insumo.getProductoActivo());

        // Activar o desactivar relaciones si cambió el estado
        if (!estabaActivo && estaActivoAhora) {
            insumo.setProductoActivo(Boolean.FALSE); //Para no afectar el metodo recursivo que lo va a activar
            articuloEstadoService.reactivarInsumoRecursivamente(insumo);
        } else if (estabaActivo && !estaActivoAhora) {
            insumo.setProductoActivo(Boolean.TRUE); //Para no afectar el metodo recursivo que lo va a activar
            articuloEstadoService.desactivarInsumoRecursivamente(insumo);
        }

        return articuloInsumoMapper.toResponse(insumo);
    }

    @Override
    public ArticuloInsumoResponse obtenerArticuloInsumo(Long id) {
        ArticuloInsumoEntity articuloInsumo = articuloInsumoRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("No se pudo encontrar el Insumo"));

        return articuloInsumoMapper.toResponse(articuloInsumo);
    }

    @Override
    @Transactional
    public void eliminarArticuloInsumo(Long id) {
        ArticuloInsumoEntity insumo = articuloInsumoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Insumo no encontrado con id: " + id));
        insumo.setEsVendible(false);

        articuloEstadoService.desactivarInsumoRecursivamente(insumo);
    }

    @Override
    public Set<ArticuloInsumoResponse> listarArticulosInsumo() {
        List<ArticuloInsumoEntity> articuloInsumoEntities = articuloInsumoRepository.findAll();

        return articuloInsumoEntities.stream().map(articuloInsumoMapper::toResponse).collect(Collectors.toSet()) ;
    }

    @Override
    public ArticuloInsumoResponse actualizarPrecioYStock(Long id, ArticuloActualizarStockPrecioRequest request) {
        // Buscar el artículo en base de datos
        ArticuloInsumoEntity articulo = articuloInsumoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artículo no encontrado con ID: " + id));

        // Guardamos el precioVenta anterior solo en caso de necesitarlo como respaldo
        BigDecimal precioVentaAnterior = articulo.getPrecioVenta();

        // Actualizar precioCosto si viene en la request
        if (request.precioCosto() != null) {
            articulo.setPrecioCosto(request.precioCosto());
        }

        // Actualizar stock si viene en la request
        if (request.stockActual() != null) {
            articulo.setStockActual(request.stockActual());
        }

        // Recalcular precioVenta: si margen es null, se usará el valor anterior
        articulo.calcularPrecioVenta(precioVentaAnterior);

        // Propagar cambios a manufacturados y promociones que dependan de este insumo
        actualizarFabricadosYPromociones(articulo);

        // Persistir cambios
        articuloInsumoRepository.save(articulo);

        // Devolver DTO de respuesta
        return articuloInsumoMapper.toResponse(articulo);
    }

    //Se usa para los checkbox de los CRUDs
    @Override
    public List<ArticuloInsumoBasicResponse> listarBasicArticulosInsumoParaPreparar() {
        return articuloInsumoRepository.basicFindAllParaPreparar();
    }

    //Es para cosas como el actualizar stock y precio
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

    public List<ArticuloInsumoMenuBasicResponse> obtenerInsumosVendibles( ) {
        List<ArticuloInsumoEntity> listaInsumos = articuloInsumoRepository.findAllVendibles();

        return listaInsumos.stream().map(articuloInsumoMapper::menuBasicResponse).toList();
    }

    public List<ArticuloInsumoResponse> obtenerInsumosBajoDeStock() {
        List<ArticuloInsumoEntity> listaInsumos = articuloInsumoRepository.insumosBajoStockFindAll();
        return listaInsumos.stream().map(articuloInsumoMapper::toResponse).toList();
    }

    //========================== METODO PARA ACTUALIZAR LOS PRECIOS EN ARTICULOS ===========================

    private void actualizarFabricadosYPromociones(ArticuloInsumoEntity insumo) {
        // 1. Actualizar Articulos Manufacturados que usen este insumo
        List<ArticuloManufacturadoDetalleEntity> detallesManufacturados =
                articuloManufacturadoDetalleRepository.findByArticuloInsumo(insumo);

        Set<ArticuloManufacturadoEntity> manufacturadosActualizados = new HashSet<>();

        for (ArticuloManufacturadoDetalleEntity detalle : detallesManufacturados) {
            ArticuloManufacturadoEntity manufacturado = detalle.getArticuloManufacturado();
            if (manufacturado != null && manufacturadosActualizados.add(manufacturado)) {
                manufacturado.calcularPrecioCosto();
                manufacturado.calcularPrecioVenta(manufacturado.getPrecioVenta());
            }
        }

        // 2. Actualizar Promociones que usen este insumo directamente
        List<PromocionDetalleEntity> detallesPromocion = promocionDetalleRepository.findByArticulo(insumo);

        Set<ArticuloPromocionEntity> promocionesActualizadas = new HashSet<>();

        for (PromocionDetalleEntity detalle : detallesPromocion) {
            ArticuloPromocionEntity promo = detalle.getArticuloPromocion();
            if (promo != null && promocionesActualizadas.add(promo)) {
                promo.calcularPrecioCosto();
                promo.calcularPrecioTotal();
                promo.calcularPrecioVenta(promo.getPrecioVenta());
            }
        }

        // 3. Actualizar promociones indirectamente (si un manufacturado cambió, también afecta promos)
        for (ArticuloManufacturadoEntity manufacturado : manufacturadosActualizados) {
            List<PromocionDetalleEntity> detallesPromoManufacturado = promocionDetalleRepository.findByArticulo(manufacturado);
            for (PromocionDetalleEntity detalle : detallesPromoManufacturado) {
                ArticuloPromocionEntity promo = detalle.getArticuloPromocion();
                if (promo != null && promocionesActualizadas.add(promo)) {
                    promo.calcularPrecioCosto();
                    promo.calcularPrecioTotal();
                    promo.calcularPrecioVenta(promo.getPrecioVenta());
                }
            }
        }
    }
}
