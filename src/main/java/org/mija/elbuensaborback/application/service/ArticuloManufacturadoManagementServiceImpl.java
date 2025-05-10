package org.mija.elbuensaborback.application.service;
import org.mija.elbuensaborback.application.dto.request.ArticuloManufacturadoCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.ArticuloManufacturadoUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoResponse;
import org.mija.elbuensaborback.application.mapper.ArticuloManufacturadoMapper;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloManufacturadoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.ArticuloManufacturadoRepositoryImpl;
import org.springframework.stereotype.Service;

@Service
public class ArticuloManufacturadoManagementServiceImpl implements ArticuloManufacturadoService {

    private final ArticuloManufacturadoRepositoryImpl articuloManufacturadoRepository;
    private final ArticuloManufacturadoMapper articuloManufacturadoMapper;

    public ArticuloManufacturadoManagementServiceImpl(ArticuloManufacturadoRepositoryImpl articuloManufacturadoRepository, ArticuloManufacturadoMapper articuloManufacturadoMapper) {
        this.articuloManufacturadoRepository = articuloManufacturadoRepository;
        this.articuloManufacturadoMapper = articuloManufacturadoMapper;
    }


    @Override
    public ArticuloManufacturadoResponse crearArticulo(ArticuloManufacturadoCreatedRequest articulo) {
        ArticuloManufacturadoEntity articuloEntity = articuloManufacturadoRepository.save(articuloManufacturadoMapper.toEntity(articulo));
        return articuloManufacturadoMapper.toResponse(articuloEntity);
    }

    @Override
    public ArticuloManufacturadoResponse actualizarArticulo(Long id, ArticuloManufacturadoUpdateRequest articulo) {
        ArticuloManufacturadoEntity articuloEntity = articuloManufacturadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artículo no encontrado con ID: " + id));
        /*System.out.println("TIENE DETALLES LA ENTIDAD TRAIDA? : ");
        articuloEntity.getArticuloManufacturadoDetalle().forEach(articuloManufacturadoDetalle -> {
            System.out.println("ID: " + articuloManufacturadoDetalle.getId());
            System.out.println("Cantidad: " + articuloManufacturadoDetalle.getCantidad());
            System.out.println("UnidadMedidaEnum: " + articuloManufacturadoDetalle.getUnidadMedidaEnum());
            System.out.println("ArticuloInsumo: " + articuloManufacturadoDetalle.getArticuloInsumo().getId());
            System.out.println("ArticuloManufacturado: " + articuloManufacturadoDetalle.getArticuloManufacturado().getId());
        });*/
        articuloManufacturadoMapper.updateEntityWithDetalles(articulo, articuloEntity);

        ArticuloManufacturadoEntity actualizado = articuloManufacturadoRepository.save(articuloEntity);

        return articuloManufacturadoMapper.toResponse(actualizado);
    }


    @Override
    public ArticuloManufacturadoResponse obtenerArticulo(Long id) {
        return articuloManufacturadoMapper.toResponse(articuloManufacturadoRepository.findById(id).orElse(null));
    }

    @Override
    public void eliminarArticulo(Long id) {
        articuloManufacturadoRepository.deleteById(id);
    }
}
