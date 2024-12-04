package edu.bbte.idde.bvim2209.spring.backend.repo.jdbc;

import edu.bbte.idde.bvim2209.spring.backend.model.BaseEntity;
import edu.bbte.idde.bvim2209.spring.backend.repo.Dao;
import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

public class JdbcDao<T extends BaseEntity> implements Dao<T> {
    @Override
    public Collection<T> findAll() {
        return List.of();
    }

    @Override
    public T findById(Long id) throws EntityNotFoundException {
        return null;
    }

    @Override
    public void create(T entity) throws IllegalArgumentException {

    }

    @Override
    public void update(T entity) throws EntityNotFoundException {

    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {

    }
}
