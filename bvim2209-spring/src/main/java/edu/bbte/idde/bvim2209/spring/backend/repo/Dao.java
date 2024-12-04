package edu.bbte.idde.bvim2209.spring.backend.repo;

import edu.bbte.idde.bvim2209.spring.backend.model.BaseEntity;

import java.util.Collection;

public interface Dao<T extends BaseEntity> {
    Collection<T> findAll();

    T findById(Long id);

    void create(T entity);

    void update(T entity);

    void delete(Long id);
}
