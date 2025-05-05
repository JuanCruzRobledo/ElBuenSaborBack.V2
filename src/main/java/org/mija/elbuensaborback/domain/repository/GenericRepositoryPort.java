package org.mija.elbuensaborback.domain.repository;

import java.util.List;
import java.util.Optional;

public interface GenericRepositoryPort <T,ID>{
    Optional<T> findById(ID id);
    List<T> findAll();
    T save(T entity);
    void deleteById(ID id);
    List<T> saveAll(List<T> entities);
}
