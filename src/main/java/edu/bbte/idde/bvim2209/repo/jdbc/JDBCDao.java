package edu.bbte.idde.bvim2209.repo.jdbc;

import edu.bbte.idde.bvim2209.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.model.BaseEntity;
import edu.bbte.idde.bvim2209.repo.Dao;

import java.util.Collection;

public abstract class JDBCDao<T extends BaseEntity> implements Dao<T> {

    @Override
    public Collection<T> findAll() {
        return null;
    }

    @Override
    public void create(T entity) throws IllegalArgumentException {

    }

    @Override
    public T findById(Long id) throws EntityNotFoundException {
        return null;
    }

    @Override
    public void update(T entity) throws EntityNotFoundException {

    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {

    }
}
