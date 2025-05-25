package org.mija.elbuensaborback.application.service;

import jakarta.persistence.EntityNotFoundException;
import org.mija.elbuensaborback.application.dto.global.categoria.CategoriaBasic;
import org.mija.elbuensaborback.application.dto.global.categoria.CategoriaDto;
import org.mija.elbuensaborback.application.dto.response.CategoriaWithSubcategoriasResponse;
import org.mija.elbuensaborback.application.mapper.CategoriaMapper;
import org.mija.elbuensaborback.application.service.contratos.CategoriaService;
import org.mija.elbuensaborback.infrastructure.persistence.entity.CategoriaEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.CategoriaRepositoryImpl;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaMapper categoriaMapper;
    private final CategoriaRepositoryImpl categoriaRepository;

    public CategoriaServiceImpl(CategoriaMapper categoriaMapper, CategoriaRepositoryImpl categoriaRepository) {
        this.categoriaMapper = categoriaMapper;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public CategoriaDto crearCategoria(CategoriaDto categoriaDto) {
        CategoriaEntity categoria = categoriaMapper.toEntity(categoriaDto);

        if (categoriaDto.categoriaPadre() != null) {
            CategoriaEntity categoriaPadre = categoriaRepository.findById(categoriaDto.categoriaPadre())
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró la categoría con id: " + categoriaDto.categoriaPadre()));
            categoria.setCategoriaPadre(categoriaPadre);
        } else {
            categoria.setCategoriaPadre(null);
        }

        CategoriaEntity categoriaGuardada = categoriaRepository.save(categoria);
        return categoriaMapper.toDto(categoriaGuardada);
    }

    @Override
    public CategoriaDto actualizarCategoria(Long id, CategoriaDto categoriaDto) {
        CategoriaEntity categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la categoría con id: " + id));

        categoriaMapper.updateEntity(categoria, categoriaDto);

        // Validar si el id de la categoría padre existe en la base de datos
        if (categoriaDto.categoriaPadre() != null) {
            boolean exists = categoriaRepository.existsById(categoriaDto.categoriaPadre());
            if (!exists) {
                throw new EntityNotFoundException("No se encontró la categoría padre con id: " + categoriaDto.categoriaPadre());
            }
        }

        return categoriaMapper.toDto(categoriaRepository.save(categoria));
    }

    @Override
    public CategoriaDto obtenerCategoria(Long id) {
        return categoriaMapper.toDto(categoriaRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("No se encontro el categoria con id: "+id)));
    }

    @Override
    public CategoriaWithSubcategoriasResponse obtenerCategoriaWithSubcategoria(Long id) {

        CategoriaEntity categoria = categoriaRepository.findWithSubcategoriasById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la categoría con id: " + id));

        Long idPadre = null;
        if (categoria.getCategoriaPadre() != null) {
            idPadre = categoria.getCategoriaPadre().getId();
        }

        CategoriaWithSubcategoriasResponse response = new CategoriaWithSubcategoriasResponse(
                categoria.getId(),
                categoria.getDenominacion(),
                idPadre ,
                categoria.getSubcategorias().stream()
                    .map(sub -> new CategoriaBasic(sub.getId(), sub.getDenominacion()))
                    .collect(Collectors.toSet()));

        return response;
    }

    @Override
    public void eliminarCategoria(Long id) {
        CategoriaEntity categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la categoría con id: " + id));
        categoriaRepository.deleteById(id);
    }

    @Override
    public Set<CategoriaDto> listarCategoria() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::toDto)
                .collect(Collectors.toSet());
    }

    public List<CategoriaDto> listarCategoriaPadres() {
         return categoriaRepository.findAllPadres().stream()
                .map(categoriaMapper::toDto)
                .toList();
    }
}
