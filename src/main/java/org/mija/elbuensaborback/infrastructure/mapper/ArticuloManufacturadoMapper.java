package org.mija.elbuensaborback.infrastructure.mapper;

import org.mija.elbuensaborback.application.dto.request.ArticuloManufacturadoCreateRequest;
import org.mija.elbuensaborback.application.dto.request.ArticuloManufacturadoDetalleRequest;
import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoDetalleResponse;
import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoResponse;
import org.mija.elbuensaborback.domain.repository.ArticuloInsumoRepositoryPort;
import org.mija.elbuensaborback.domain.repository.CategoriaRepositoryPort;
import org.mija.elbuensaborback.domain.repository.SucursalRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloInsumoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloManufacturadoDetalleEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloManufacturadoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ImagenArticuloEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ArticuloManufacturadoMapper {

    private final CategoriaRepositoryPort categoriaRepository;
    private final SucursalRepositoryPort sucursalRepository;
    private final ArticuloInsumoRepositoryPort articuloInsumoRepository;

    public ArticuloManufacturadoMapper(CategoriaRepositoryPort categoriaRepository,
                                       SucursalRepositoryPort sucursalRepository,
                                       ArticuloInsumoRepositoryPort articuloInsumoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.sucursalRepository = sucursalRepository;
        this.articuloInsumoRepository = articuloInsumoRepository;
    }

    public ArticuloManufacturadoEntity toEntity(ArticuloManufacturadoCreateRequest request) {
        ArticuloManufacturadoEntity articulo = new ArticuloManufacturadoEntity();

        // Mapeo de propiedades básicas
        articulo.setDenominacion(request.denominacion());
        articulo.setPrecioVenta(request.precioVenta());
        articulo.setProductoActivo(true); // Por defecto activo al crear
        articulo.setDescripcion(request.descripcion());
        articulo.setPrecioCosto(request.precioCosto());
        articulo.setTiempoEstimadoMinutos(request.tiempoEstimadoMinutos());
        articulo.setUnidadMedidaEnum(request.unidadMedidaEnum());

        // Mapeo de relaciones
        articulo.setCategoria(categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada")));

        articulo.setSucursal(sucursalRepository.findById(request.sucursalId())
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrada")));

        // Mapeo de imágenes (asumiendo que tienes una entidad ImagenArticuloEntity)
        Set<ImagenArticuloEntity> imagenes = request.imagenesUrls().stream()
                .map(url -> {
                    ImagenArticuloEntity imagen = new ImagenArticuloEntity();
                    imagen.setDenominacion(url);
                    imagen.setArticulo(articulo);
                    return imagen;
                })
                .collect(Collectors.toSet());
        articulo.setImagenesUrls(imagenes);

        // Mapeo de detalles
        List<ArticuloManufacturadoDetalleEntity> detalles = request.articuloManufacturadoDetalle().stream()
                .map(detalleRequest -> toDetalleEntity(detalleRequest, articulo))
                .collect(Collectors.toList());
        articulo.setArticuloManufacturadoDetalle(detalles);

        articulo.pesoTotal();

        return articulo;
    }

    private ArticuloManufacturadoDetalleEntity toDetalleEntity(
            ArticuloManufacturadoDetalleRequest detalleRequest,
            ArticuloManufacturadoEntity articulo) {

        ArticuloManufacturadoDetalleEntity detalle = new ArticuloManufacturadoDetalleEntity();
        detalle.setCantidad(detalleRequest.cantidad());
        detalle.setUnidadMedidaEnum(detalleRequest.unidadMedidaEnum());
        detalle.setArticuloManufacturado(articulo);

        ArticuloInsumoEntity insumo = articuloInsumoRepository.findById(detalleRequest.articuloInsumoId())
                .orElseThrow(() -> new RuntimeException("Artículo insumo no encontrado"));
        detalle.setArticuloInsumo(insumo);

        return detalle;
    }

    //RESPUESTAS
    public ArticuloManufacturadoResponse toResponse(ArticuloManufacturadoEntity entity) {
        if (entity == null) {
            return null;
        }

        return new ArticuloManufacturadoResponse(
                entity.getId(),
                entity.getDenominacion(),
                entity.getPrecioVenta(),
                entity.getProductoActivo(),
                mapImagenesToUrls(entity.getImagenesUrls()),
                entity.getDescripcion(),
                entity.getPrecioCosto(),
                entity.getTiempoEstimadoMinutos(),
                entity.getPesoTotal(),
                entity.getUnidadMedidaEnum(),
                entity.getCategoria().getId(),
                entity.getCategoria().getDenominacion(), // Asume que CategoriaEntity tiene "denominacion"
                entity.getSucursal().getId(),
                entity.getSucursal().getNombre(), // Asume que SucursalEntity tiene "nombre"
                mapDetallesToResponse(entity.getArticuloManufacturadoDetalle())
        );
    }

    private Set<String> mapImagenesToUrls(Set<ImagenArticuloEntity> imagenes) {
        if (imagenes == null) {
            return Set.of();
        }
        return imagenes.stream()
                .map(ImagenArticuloEntity::getDenominacion)
                .collect(Collectors.toSet());
    }

    private List<ArticuloManufacturadoDetalleResponse> mapDetallesToResponse(
            List<ArticuloManufacturadoDetalleEntity> detalles) {
        if (detalles == null) {
            return List.of();
        }
        return detalles.stream()
                .map(this::toDetalleResponse)
                .collect(Collectors.toList());
    }

    private ArticuloManufacturadoDetalleResponse toDetalleResponse(
            ArticuloManufacturadoDetalleEntity detalle) {
        return new ArticuloManufacturadoDetalleResponse(
                detalle.getId(),
                detalle.getArticuloInsumo().getId(),
                detalle.getArticuloInsumo().getDenominacion(), // Asume que ArticuloInsumoEntity tiene "denominacion"
                detalle.getCantidad(),
                detalle.getUnidadMedidaEnum()
        );
    }
}