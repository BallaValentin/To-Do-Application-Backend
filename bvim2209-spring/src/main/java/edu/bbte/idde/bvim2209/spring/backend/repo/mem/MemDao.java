package edu.bbte.idde.bvim2209.spring.backend.repo.mem;

import edu.bbte.idde.bvim2209.spring.backend.model.BaseEntity;
import edu.bbte.idde.bvim2209.spring.backend.repo.Dao;
import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public abstract class MemDao<T extends BaseEntity> implements Dao<T> {
    protected transient Map<Long, T> entities = new ConcurrentHashMap<>();
    private static final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Collection<T> findAll() {
        return entities.values().stream()
                .filter(entity -> !entity.getDeleted())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<T> findById(Long id) {
        if (entities.containsKey(id) && !entities.get(id).getDeleted()) {
            return Optional.of(entities.get(id));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void create(T entity) throws IllegalArgumentException {
        if (entity.getId() == null) {
            entity.setId(idCounter.getAndIncrement());
            entity.setDeleted(false);
        } else if (entities.containsKey(entity.getId())) {
            throw new IllegalArgumentException("Entity with ID " + entity.getId() + " already exists");
        }
        entities.put(entity.getId(), entity);
    }

    @Override
    public void update(T entity) throws EntityNotFoundException {
        Long id = entity.getId();
        if (entities.containsKey(id) && !entity.getDeleted()) {
            entities.put(id, entity);
        } else {
            throw new EntityNotFoundException("Entity with ID " + id + " not found in memory");
        }
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException {

        if (entities.containsKey(id) && !entities.get(id).getDeleted()) {
            T entity = entities.get(id);
            entity.setDeleted(true);
            entities.put(id, entity);
        } else {
            throw new EntityNotFoundException("Entity with ID " + id + " not found in memory");
        }
    }
}
