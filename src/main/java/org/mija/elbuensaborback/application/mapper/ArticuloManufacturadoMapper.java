package org.mija.elbuensaborback.application.mapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.request.*;
import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoDetalleResponse;
import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoResponse;
import org.mija.elbuensaborback.application.dto.response.ImagenResponse;
import org.mija.elbuensaborback.domain.repository.*;
import org.mija.elbuensaborback.infrastructure.persistence.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ArticuloManufacturadoMapper {

    // Repositorios
    private final CategoriaRepositoryPort categoriaRepository;
    private final SucursalRepositoryPort sucursalRepository;
    private final ArticuloInsumoRepositoryPort articuloInsumoRepository;
    private final ArticuloManufacturadoDetalleRepositoryPort articuloManufacturadoDetalleRepository;
    private final ImagenArticuloRepositoryPort imagenArticuloRepository;

    // Mensajes de error constantes
    private static final String CATEGORIA_NOT_FOUND = "Categoría no encontrada";
    private static final String SUCURSAL_NOT_FOUND = "Sucursal no encontrada";
    private static final String INSUMO_NOT_FOUND = "Artículo insumo no encontrado";
    private static final String IMAGEN_NOT_FOUND = "Imagen de artículo no encontrada";
    private static final String DETALLE_NOT_FOUND = "Detalle no encontrado";

    // ---- MÉTODOS PRINCIPALES ----

    public ArticuloManufacturadoEntity DtoCreatedToEntity(ArticuloManufacturadoCreatedRequest request) {
        ArticuloManufacturadoEntity articulo = new ArticuloManufacturadoEntity();

        mapCommonProperties(request, articulo);
        articulo.setCategoria(getCategoria(request.categoriaId()));
        articulo.setSucursal(getSucursal(request.sucursalId()));
        articulo.setImagenesUrls(mapImagenesFromUrls(request.imagenesUrls(), articulo));
        articulo.setArticuloManufacturadoDetalle(mapDetallesFromRequest(request.articuloManufacturadoDetalle(), articulo));

        articulo.pesoTotal();
        return articulo;
    }

    public ArticuloManufacturadoEntity DtoUpdateToEntity(ArticuloManufacturadoUpdateRequest request) {
        ArticuloManufacturadoEntity articulo = new ArticuloManufacturadoEntity();

        mapCommonProperties(request, articulo);
        articulo.setCategoria(getCategoria(request.categoriaId()));
        articulo.setSucursal(getSucursal(request.sucursalId()));
        articulo.setImagenesUrls(mapImagenesFromUpdateRequest(request.imagenesUrls(), articulo));
        articulo.setArticuloManufacturadoDetalle(mapDetallesFromUpdateRequest(request.articuloManufacturadoDetalle(), articulo));

        articulo.pesoTotal();
        return articulo;
    }

    public ArticuloManufacturadoResponse toResponse(ArticuloManufacturadoEntity entity) {
        if (entity == null) return null;

        return new ArticuloManufacturadoResponse(
                entity.getId(),
                entity.getDenominacion(),
                entity.getPrecioVenta(),
                entity.getProductoActivo(),
                entity.getDescripcion(),
                entity.getPrecioCosto(),
                entity.getTiempoEstimadoMinutos(),
                entity.getPesoTotal(),
                entity.getUnidadMedidaEnum(),
                entity.getCategoria().getId(),
                entity.getCategoria().getDenominacion(),
                entity.getSucursal().getId(),
                entity.getSucursal().getNombre(),
                mapImagenesToResponses(entity.getImagenesUrls()),
                mapDetallesToResponses(entity.getArticuloManufacturadoDetalle())
        );
    }

    // ---- HELPERS PRIVADOS ----

    private void mapCommonProperties(ArticuloManufacturadoBaseRequest request,
                                     ArticuloManufacturadoEntity articulo) {
        articulo.setDenominacion(request.denominacion());
        articulo.setPrecioVenta(request.precioVenta());
        articulo.setProductoActivo(true);
        articulo.setDescripcion(request.descripcion());
        articulo.setPrecioCosto(request.precioCosto());
        articulo.setTiempoEstimadoMinutos(request.tiempoEstimadoMinutos());
        articulo.setUnidadMedidaEnum(request.unidadMedidaEnum());
    }

    private CategoriaEntity getCategoria(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CATEGORIA_NOT_FOUND));
    }

    private SucursalEntity getSucursal(Long id) {
        return sucursalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(SUCURSAL_NOT_FOUND));
    }

    private ArticuloInsumoEntity getArticuloInsumo(Long id) {
        return articuloInsumoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(INSUMO_NOT_FOUND));
    }

    private Set<ImagenArticuloEntity> mapImagenesFromUrls(Set<String> urls,
                                                          ArticuloManufacturadoEntity articulo) {
        return urls.stream()
                .map(url -> ImagenArticuloEntity.builder().articulo(articulo).denominacion(url).build())
                .collect(Collectors.toSet());
    }

    private Set<ImagenArticuloEntity> mapImagenesFromUpdateRequest(
            Set<ImagenRequest> imagenesRequests,
            ArticuloManufacturadoEntity articulo) {
        return imagenesRequests.stream()
                .map(imagenRequest -> {
                    if (imagenRequest.id() != null) {
                        imagenArticuloRepository.findById(imagenRequest.id())
                                .orElseThrow(() -> new EntityNotFoundException(IMAGEN_NOT_FOUND));
                    }
                    return new ImagenArticuloEntity(
                            imagenRequest.id(),
                            imagenRequest.denominacion(),
                            articulo
                    );
                })
                .collect(Collectors.toSet());
    }

    private List<ArticuloManufacturadoDetalleEntity> mapDetallesFromRequest(
            List<ArticuloManufacturadoDetalleCreatedRequest> detallesRequests,
            ArticuloManufacturadoEntity articulo) {
        return detallesRequests.stream()
                .map(detalleRequest -> createDetalleEntity(detalleRequest, articulo))
                .collect(Collectors.toList());
    }

    private List<ArticuloManufacturadoDetalleEntity> mapDetallesFromUpdateRequest(
            List<ArticuloManufacturadoDetalleUpdateRequest> detallesRequests,
            ArticuloManufacturadoEntity articulo) {
        return detallesRequests.stream()
                .map(detalleRequest -> updateDetalleEntity(detalleRequest, articulo))
                .collect(Collectors.toList());
    }

    private ArticuloManufacturadoDetalleEntity createDetalleEntity(
            ArticuloManufacturadoDetalleCreatedRequest detalleRequest,
            ArticuloManufacturadoEntity articulo) {

        return ArticuloManufacturadoDetalleEntity.builder()
                .cantidad(detalleRequest.cantidad())
                .unidadMedidaEnum(detalleRequest.unidadMedidaEnum())
                .articuloManufacturado(articulo)
                .articuloInsumo(getArticuloInsumo(detalleRequest.articuloInsumoId()))
                .build();
    }

    private ArticuloManufacturadoDetalleEntity updateDetalleEntity(
            ArticuloManufacturadoDetalleUpdateRequest detalleRequest,
            ArticuloManufacturadoEntity articulo) {

        articuloManufacturadoDetalleRepository.findById(detalleRequest.id())
                .orElseThrow(() -> new EntityNotFoundException(DETALLE_NOT_FOUND));

        return ArticuloManufacturadoDetalleEntity.builder()
                .id(detalleRequest.id())
                .cantidad(detalleRequest.cantidad())
                .unidadMedidaEnum(detalleRequest.unidadMedidaEnum())
                .articuloManufacturado(articulo)
                .articuloInsumo(getArticuloInsumo(detalleRequest.articuloInsumoId()))
                .build();
    }

    private Set<ImagenResponse> mapImagenesToResponses(Set<ImagenArticuloEntity> imagenes) {
        return imagenes == null ? Set.of() : imagenes.stream()
                .map(imagen -> new ImagenResponse(imagen.getId(), imagen.getDenominacion()))
                .collect(Collectors.toSet());
    }

    private List<ArticuloManufacturadoDetalleResponse> mapDetallesToResponses(
            List<ArticuloManufacturadoDetalleEntity> detalles) {
        return detalles == null ? List.of() : detalles.stream()
                .map(this::mapDetalleToResponse)
                .collect(Collectors.toList());
    }

    private ArticuloManufacturadoDetalleResponse mapDetalleToResponse(
            ArticuloManufacturadoDetalleEntity detalle) {
        return new ArticuloManufacturadoDetalleResponse(
                detalle.getId(),
                detalle.getArticuloInsumo().getId(),
                detalle.getArticuloInsumo().getDenominacion(),
                detalle.getCantidad(),
                detalle.getUnidadMedidaEnum()
        );
    }
}