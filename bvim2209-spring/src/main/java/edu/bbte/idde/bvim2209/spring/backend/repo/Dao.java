package edu.bbte.idde.bvim2209.spring.backend.repo;

import edu.bbte.idde.bvim2209.spring.backend.model.BaseEntity;
import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;

import java.util.Collection;

public interface Dao<T extends BaseEntity> {
    Collection<T> findAll();

    T getById(Long id) throws EntityNotFoundException;

    T saveAndFlush(T entity) throws IllegalArgumentException;

    void update(T entity) throws EntityNotFoundException;

    void deleteById(Long id) throws EntityNotFoundException;
}
