package org.mija.elbuensaborback.application.service;
import org.mija.elbuensaborback.application.dto.global.manufacturado.ArticuloManufacturadoDetalleDto;
import org.mija.elbuensaborback.application.dto.request.manufacturado.ArticuloManufacturadoCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.manufacturado.ArticuloManufacturadoUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoBasicResponse;
import org.mija.elbuensaborback.application.dto.response.ArticuloManufacturadoResponse;
import org.mija.elbuensaborback.application.mapper.ArticuloManufacturadoMapper;
import org.mija.elbuensaborback.application.service.contratos.ArticuloManufacturadoService;
import org.mija.elbuensaborback.infrastructure.persistence.entity.*;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.ArticuloInsumoRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.ArticuloManufacturadoDetalleRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.ArticuloManufacturadoRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.CategoriaRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticuloManufacturadoServiceImpl implements ArticuloManufacturadoService {

    private final ArticuloManufacturadoRepositoryImpl articuloManufacturadoRepository;
    private final CategoriaRepositoryImpl categoriaRepository;
    private final ArticuloManufacturadoDetalleRepositoryImpl articuloManufacturadoDetalleRepository;
    private final ArticuloManufacturadoMapper articuloManufacturadoMapper;
    private final ArticuloInsumoRepositoryImpl articuloInsumoRepository;

    public ArticuloManufacturadoServiceImpl(ArticuloManufacturadoRepositoryImpl articuloManufacturadoRepository, CategoriaRepositoryImpl categoriaRepository, ArticuloManufacturadoDetalleRepositoryImpl articuloManufacturadoDetalleRepository, ArticuloManufacturadoMapper articuloManufacturadoMapper, ArticuloInsumoRepositoryImpl articuloInsumoRepository) {
        this.articuloManufacturadoRepository = articuloManufacturadoRepository;
        this.categoriaRepository = categoriaRepository;
        this.articuloManufacturadoDetalleRepository = articuloManufacturadoDetalleRepository;
        this.articuloManufacturadoMapper = articuloManufacturadoMapper;
        this.articuloInsumoRepository = articuloInsumoRepository;
    }


    @Override
    public ArticuloManufacturadoResponse crearArticulo(ArticuloManufacturadoCreatedRequest articulo) {
        CategoriaEntity categoria = categoriaRepository.findById(articulo.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        ArticuloManufacturadoEntity articuloEntity = articuloManufacturadoMapper.toEntity(articulo);
        articuloEntity.setCategoria(categoria);

        SucursalEntity sucursal = SucursalEntity.builder().id(1L).build();
        articuloEntity.setSucursal(sucursal);

        articuloEntity.calcularPrecioCosto();

        System.out.println("COSTO CALCULADO: "+ articuloEntity.getPrecioCosto());
        System.out.println("COSTO RECIBIDO: "+ articulo.precioCosto());

        articuloEntity.setPrecioCosto(articulo.precioCosto());
        articuloEntity.setPrecioVenta(articulo.precioVenta());
        articuloEntity.setTiempoEstimadoMinutos(articulo.tiempoEstimadoMinutos());

        articuloEntity = articuloManufacturadoRepository.save(articuloEntity);

        List<ArticuloManufacturadoDetalleEntity> listaDetalles =
                articuloManufacturadoDetalleRepository.findAllByIdArticuloManufacturado(articuloEntity.getId());

        articuloEntity.setArticuloManufacturadoDetalle(listaDetalles);

        return articuloManufacturadoMapper.toResponse(articuloEntity);
    }

    @Override
    public ArticuloManufacturadoResponse actualizarArticulo(Long id, ArticuloManufacturadoUpdateRequest articulo) {
        /*System.out.println("TIENE DETALLES LA ENTIDAD TRAIDA? : ");
        articuloEntity.getArticuloManufacturadoDetalle().forEach(articuloManufacturadoDetalle -> {
            System.out.println("ID: " + articuloManufacturadoDetalle.getId());
            System.out.println("Cantidad: " + articuloManufacturadoDetalle.getCantidad());
            System.out.println("UnidadMedidaEnum: " + articuloManufacturadoDetalle.getUnidadMedidaEnum());
            System.out.println("ArticuloInsumo: " + articuloManufacturadoDetalle.getArticuloInsumo().getId());
            System.out.println("ArticuloManufacturado: " + articuloManufacturadoDetalle.getArticuloManufacturado().getId());
        });*/

        // 1. Buscar el artículo existente
        ArticuloManufacturadoEntity articuloEntity = articuloManufacturadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artículo no encontrado con ID: " + id));

        // 2. Buscar la categoría
        CategoriaEntity categoria = categoriaRepository.findById(articulo.categoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + articulo.categoriaId()));

        // 3. Obtener todos los IDs de insumos mencionados en los detalles
        List<Long> insumoIds = articulo.articuloManufacturadoDetalle().stream()
                .map(ArticuloManufacturadoDetalleDto::articuloInsumoId)
                .distinct()
                .toList();

        // 4. Buscar todos los insumos y armar un Map por ID
        Map<Long, ArticuloInsumoEntity> insumos = articuloInsumoRepository.findAllById(insumoIds).stream()
                .collect(Collectors.toMap(ArticuloInsumoEntity::getId, Function.identity()));

        // 5. Validar que no falte ninguno
        if (insumos.size() != insumoIds.size()) {
            throw new RuntimeException("Uno o más insumos no existen en la base de datos.");
        }

        // 6. Mapear datos desde el DTO hacia la entidad
        articuloManufacturadoMapper.updateEntityWithDetalles(articulo, articuloEntity, insumos);

        if (articuloEntity.getPrecioCosto() != articulo.precioCosto()){
            throw new RuntimeException("Precio costo insuficiente");
        }
        articuloEntity.setPrecioCosto(articulo.precioCosto());
        articuloEntity.setPrecioVenta(articulo.precioVenta());
        articuloEntity.setTiempoEstimadoMinutos(articulo.tiempoEstimadoMinutos());

        // 7. Setear la nueva categoría
        articuloEntity.setCategoria(categoria);

        // 8. Guardar cambios
        ArticuloManufacturadoEntity actualizado = articuloManufacturadoRepository.save(articuloEntity);

        // 9. Devolver respuesta mapeada
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
        System.out.println("IMAGENES");
        articulos.get(0).getImagenesUrls().forEach(image -> System.out.println(image));
        return articulos.stream().map(articuloManufacturadoMapper::toResponse).collect(Collectors.toSet());
    }

    public List<ArticuloManufacturadoBasicResponse> listarBasicArticulosManufacturados() {
        //AGREGAR QUERY PERSONALIZADA
        //basicFindAll
        List<ArticuloManufacturadoEntity> articulos = articuloManufacturadoRepository.findAll();
        return articulos.stream().map(articuloManufacturadoMapper::toBasicResponse).collect(Collectors.toList());
    }

}
