package edu.bbte.idde.bvim2209.spring.backend.repo;

import edu.bbte.idde.bvim2209.spring.backend.model.BaseEntity;
import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;

import java.util.Collection;
import java.util.Optional;

public interface Dao<T extends BaseEntity> {
    Collection<T> findAll();

    Optional<T> findById(Long id);

    void create(T entity) throws IllegalArgumentException;

    void update(T entity) throws EntityNotFoundException;

    void deleteById(Long id) throws EntityNotFoundException;
}
