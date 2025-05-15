package org.mija.elbuensaborback.application.dto.response;

import org.mija.elbuensaborback.application.dto.global.categoria.CategoriaBasic;

import java.util.Set;

public record CategoriaWithSubcategoriasResponse(
        Long id,
        String denominacion,
        Long categoriaPadre,
        Set<CategoriaBasic> subcategorias
) {
}
