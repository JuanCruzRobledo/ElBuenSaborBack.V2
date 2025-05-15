package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.CategoriaRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.CategoriaEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.CategoriaJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoriaRepositoryImpl implements CategoriaRepositoryPort {

    private final CategoriaJpaRepository categoriaJpaRepository;

    public CategoriaRepositoryImpl(CategoriaJpaRepository categoriaJpaRepository) {
        this.categoriaJpaRepository = categoriaJpaRepository;
    }

    @Override
    public Optional<CategoriaEntity> findById(Long id) {
        return categoriaJpaRepository.findById(id);
    }

    @Override
    public List<CategoriaEntity> findAll() {
        return categoriaJpaRepository.findAll();
    }

    @Override
    public CategoriaEntity save(CategoriaEntity nombreEntidad) {
        return categoriaJpaRepository.save(nombreEntidad);
    }

    @Override
    public void deleteById(Long id) {
        categoriaJpaRepository.deleteById(id);
    }

    @Override
    public List<CategoriaEntity> saveAll(List<CategoriaEntity> categoriaEntities) {
        return categoriaJpaRepository.saveAll(categoriaEntities);
    }
    public boolean existsById(Long id) {
        return categoriaJpaRepository.existsById(id);
    }

    public Optional<CategoriaEntity> findWithSubcategoriasById(Long id){
        return categoriaJpaRepository.findWithSubcategoriasById(id);
    }

}
