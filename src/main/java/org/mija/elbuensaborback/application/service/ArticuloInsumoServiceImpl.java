package org.mija.elbuensaborback.application.service;

import jakarta.persistence.EntityNotFoundException;
import org.mija.elbuensaborback.application.dto.request.insumo.ArticuloInsumoCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.insumo.ArticuloInsumoUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ArticuloInsumoBasicResponse;
import org.mija.elbuensaborback.application.dto.response.ArticuloInsumoResponse;
import org.mija.elbuensaborback.application.mapper.ArticuloInsumoMapper;
import org.mija.elbuensaborback.application.service.contratos.ArticuloInsumoService;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloInsumoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.CategoriaEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.ArticuloInsumoRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.CategoriaRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticuloInsumoServiceImpl implements ArticuloInsumoService {

    private final CategoriaRepositoryImpl categoriaRepository;
    private final ArticuloInsumoRepositoryImpl articuloInsumoRepository;
    private final ArticuloInsumoMapper articuloInsumoMapper;

    public ArticuloInsumoServiceImpl(CategoriaRepositoryImpl categoriaRepository, ArticuloInsumoRepositoryImpl articuloInsumoRepository, ArticuloInsumoMapper articuloInsumoMapper) {
        this.categoriaRepository = categoriaRepository;
        this.articuloInsumoRepository = articuloInsumoRepository;
        this.articuloInsumoMapper = articuloInsumoMapper;
    }


    @Override
    public ArticuloInsumoResponse crearArticuloInsumo(ArticuloInsumoCreatedRequest articuloCreatedRequest) {

        CategoriaEntity categoria =  categoriaRepository.findById(articuloCreatedRequest.categoriaId()).orElseThrow(()-> new EntityNotFoundException("No se encontro la categoria"));
        ArticuloInsumoEntity insumo = articuloInsumoMapper.toEntity(articuloCreatedRequest);
        insumo.setCategoria(categoria);

        return articuloInsumoMapper.toResponse(articuloInsumoRepository.save(insumo));
    }

    @Override
    public ArticuloInsumoResponse actualizarArticuloInsumo(Long id, ArticuloInsumoUpdateRequest articuloUpdateRequest) {

        CategoriaEntity categoria =  categoriaRepository.findById(articuloUpdateRequest.categoriaId()).orElseThrow(()-> new EntityNotFoundException("No se encontro la categoria"));
        ArticuloInsumoEntity insumo =  articuloInsumoRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("No se pudo encontrar el Insumo"));
        articuloInsumoMapper.updateEntity(insumo, articuloUpdateRequest);

        //Se asigna despues de la actualizacion ya que la categoria se ignora en el mapper
        insumo.setCategoria(categoria);
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
    public void eliminarArticuloInsumo(Long id) {
        articuloInsumoRepository.deleteById(id);
    }

    @Override
    public Set<ArticuloInsumoResponse> listarArticulosInsumo() {
        List<ArticuloInsumoEntity> articuloInsumoEntities = articuloInsumoRepository.findAll();

        return articuloInsumoEntities.stream().map(articuloInsumoMapper::toResponse).collect(Collectors.toSet()) ;
    }


    public List<ArticuloInsumoBasicResponse> listarBasicArticulosInsumo() {
        return articuloInsumoRepository.basicFindAll();
    }

}
