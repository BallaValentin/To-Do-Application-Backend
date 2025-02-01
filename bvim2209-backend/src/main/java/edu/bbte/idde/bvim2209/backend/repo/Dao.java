package edu.bbte.idde.bvim2209.backend.repo;

import edu.bbte.idde.bvim2209.backend.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.backend.model.BaseEntity;

import java.util.Collection;

/**
 * CRUD műveleteket absztraktan meghatározó interfész.
 * Azok a műveletek kerülnek ide, melyek minden entitásban szükségesen
 * támogatottak.
 * <p>
 * Generikus bármilyen modellosztályra, mely örökli a BaseEntity-t.
 */
public interface Dao<T extends BaseEntity> {

    /**
     * Teljes entitáslista visszatérítése.
     */
    Collection<T> findAll();

    /**
     * Létrehozás entitás által
     */
    void create(T entity) throws IllegalArgumentException;

    T findById(Long id) throws EntityNotFoundException;

    void update(T entity) throws EntityNotFoundException;

    void delete(Long id) throws EntityNotFoundException;

    Boolean getLogQueries();
    Boolean getLogUpdates();

    Integer getLogQueriesCount();
    Integer getLogUpdatesCount();
}
