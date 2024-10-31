package edu.bbte.idde.bvim2209.repo;

import edu.bbte.idde.bvim2209.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.model.BaseEntity;

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

    void update(T entity) throws EntityNotFoundException;

    void delete(Long id) throws EntityNotFoundException;
}
