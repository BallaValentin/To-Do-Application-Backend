package edu.bbte.idde.bvim2209.spring.backend.repo.mem;

import edu.bbte.idde.bvim2209.spring.backend.model.BaseEntity;
import edu.bbte.idde.bvim2209.spring.backend.repo.Dao;
import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class MemDao<T extends BaseEntity> implements Dao<T> {
    protected transient Map<Long, T> entities = new ConcurrentHashMap<>();
    private static final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Page<T> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<T> findById(Long id) {
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public T saveAndFlush(T entity) throws IllegalArgumentException {
        if (entity.getId() == null) {
            entity.setId(idCounter.getAndIncrement());
        } else if (entities.containsKey(entity.getId())) {
            throw new IllegalArgumentException("Entity with ID " + entity.getId() + " already exists");
        }
        entities.put(entity.getId(), entity);
        return entity;
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
    public void deleteById(Long id) throws EntityNotFoundException {
        if (entities.containsKey(id)) {
            entities.remove(id);
        } else {
            throw new EntityNotFoundException("Entity with ID " + id + " not found in memory");
        }
    }
}
