package org.mija.elbuensaborback.application.service.contratos;

import org.mija.elbuensaborback.application.dto.global.categoria.CategoriaDto;
import org.mija.elbuensaborback.application.dto.response.CategoriaWithSubcategoriasResponse;

import java.util.Set;

public interface CategoriaService {
    CategoriaDto crearCategoria(CategoriaDto categoriaDto);
    CategoriaDto actualizarCategoria(Long id , CategoriaDto categoriaDto);
    CategoriaDto obtenerCategoria(Long id);
    CategoriaWithSubcategoriasResponse obtenerCategoriaWithSubcategoria(Long id);
    void eliminarCategoria(Long id);
    Set<CategoriaDto> listarCategoria();
}
