package edu.bbte.idde.bvim2209.spring.backend.repo.mem;

import edu.bbte.idde.bvim2209.spring.backend.model.BaseEntity;
import edu.bbte.idde.bvim2209.spring.backend.repo.Dao;
import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class MemDao<T extends BaseEntity> implements Dao<T> {
    protected transient Map<Long, T> entities = new ConcurrentHashMap<>();
    private static final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Collection<T> findAll() {
        return entities.values();
    }

    @Override
    public T findById(Long id) {
        T entity = entities.get(id);
        if (entity == null) {
            throw new EntityNotFoundException("Entity with ID " + id + " not found in memory");
        }
        return entity;
    }

    @Override
    public void create(T entity) throws IllegalArgumentException {
        if (entity.getId() == null) {
            entity.setId(idCounter.getAndIncrement());
        } else if (entities.containsKey(entity.getId())) {
            throw new IllegalArgumentException("Entity with ID " + entity.getId() + " already exists");
        }
        entities.put(entity.getId(), entity);
    }

    @Override
    public void update(T entity) throws EntityNotFoundException {
        Long id = entity.getId();
        if (entities.containsKey(id)) {
            entities.put(id, entity);
        } else {
            throw new EntityNotFoundException("Entity with ID " + id + " not found in memory");
        }
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        if (entities.containsKey(id)) {
            entities.remove(id);
        } else {
            throw new EntityNotFoundException("Entity with ID " + id + " not found in memory");
        }
    }
}
