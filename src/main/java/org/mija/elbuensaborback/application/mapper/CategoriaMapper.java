package org.mija.elbuensaborback.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mija.elbuensaborback.application.dto.global.categoria.CategoriaBasic;
import org.mija.elbuensaborback.application.dto.global.categoria.CategoriaDto;
import org.mija.elbuensaborback.application.dto.response.CategoriaWithSubcategoriasResponse;
import org.mija.elbuensaborback.infrastructure.persistence.entity.CategoriaEntity;

@Mapper(componentModel = "spring")
public abstract class CategoriaMapper {

    @Mapping(target = "categoriaPadre.id", source = "categoriaPadre")
    public abstract CategoriaEntity toEntity(CategoriaDto categoriaDto);

    @Mapping(target = "categoriaPadre", source = "categoriaPadre.id")
    public abstract CategoriaDto toDto(CategoriaEntity categoriaEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoriaPadre", source = "categoriaPadre", qualifiedByName = "mapCategoriaPadre")
    public abstract void updateEntity(@MappingTarget CategoriaEntity categoriaEntity, CategoriaDto categoriaDto);

    @Named("mapCategoriaPadre")
    protected CategoriaEntity mapCategoriaPadre(Long id) {
        if (id == null) return null;
        CategoriaEntity padre = new CategoriaEntity();
        padre.setId(id);
        return padre;
    }

    public abstract CategoriaBasic toBasic(CategoriaEntity categoriaEntity);

    @Mapping(target = "categoriaPadre", source = "categoriaPadre.id")
    public abstract CategoriaWithSubcategoriasResponse toWithSubcategoriasResponse(CategoriaEntity categoriaEntity);
}
