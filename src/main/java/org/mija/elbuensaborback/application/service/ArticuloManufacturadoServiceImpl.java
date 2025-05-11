package org.mija.elbuensaborback.application.service;
import org.mija.elbuensaborback.application.dto.request.manufacturado.ArticuloManufacturadoCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.manufacturado.ArticuloManufacturadoUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoBasicResponse;
import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoResponse;
import org.mija.elbuensaborback.application.mapper.ArticuloManufacturadoMapper;
import org.mija.elbuensaborback.application.service.contratos.ArticuloManufacturadoService;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloManufacturadoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.ArticuloManufacturadoRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticuloManufacturadoServiceImpl implements ArticuloManufacturadoService {

    private final ArticuloManufacturadoRepositoryImpl articuloManufacturadoRepository;
    private final ArticuloManufacturadoMapper articuloManufacturadoMapper;

    public ArticuloManufacturadoServiceImpl(ArticuloManufacturadoRepositoryImpl articuloManufacturadoRepository, ArticuloManufacturadoMapper articuloManufacturadoMapper) {
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

    @Override
    public Set<ArticuloManufacturadoResponse> listarArticulos() {

        List<ArticuloManufacturadoEntity> articulos = articuloManufacturadoRepository.findAll();
        return articulos.stream().map(articuloManufacturadoMapper::toResponse).collect(Collectors.toSet());
    }

    public List<ArticuloManufacturadoBasicResponse> listarBasicArticulosManufacturados() {
        //AGREGAR QUERY PERSONALIZADA
        //basicFindAll
        List<ArticuloManufacturadoEntity> articulos = articuloManufacturadoRepository.findAll();
        return articulos.stream().map(articuloManufacturadoMapper::toBasicResponse).collect(Collectors.toList());
    }

}
