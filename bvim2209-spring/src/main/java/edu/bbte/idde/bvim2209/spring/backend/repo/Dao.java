package edu.bbte.idde.bvim2209.spring.backend.repo;

import edu.bbte.idde.bvim2209.spring.backend.model.BaseEntity;
import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;

import java.util.Collection;

public interface Dao<T extends BaseEntity> {
    Collection<T> findAll();

    T findById(Long id) throws EntityNotFoundException;

    void create(T entity) throws IllegalArgumentException;

    void update(T entity) throws EntityNotFoundException;

    void delete(Long id) throws EntityNotFoundException;
}
