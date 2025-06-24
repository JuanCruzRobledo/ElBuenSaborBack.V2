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

        // Guardar estado anterior
        boolean estabaActivo = Boolean.TRUE.equals(insumo.getProductoActivo());

        // Mapear cambios
        articuloInsumoMapper.updateEntity(insumo, articuloUpdateRequest);
        insumo.setCategoria(categoria);

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

        System.out.println("IMAGENES: " );
        articuloInsumo.getImagenesUrls().forEach(imagen -> System.out.println("IMAGEN ID: "+imagen.getId() + "IMAGEN DENOMINACION: "+ imagen.getUrl()));
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
}
