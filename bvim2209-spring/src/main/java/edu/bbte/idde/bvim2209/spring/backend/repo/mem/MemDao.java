package edu.bbte.idde.bvim2209.spring.backend.repo.mem;

import edu.bbte.idde.bvim2209.spring.backend.model.BaseEntity;
import edu.bbte.idde.bvim2209.spring.backend.repo.Dao;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Primary
@Repository
public abstract class MemDao<T extends BaseEntity> implements Dao<T> {
    protected transient Map<Long, T> entities = new ConcurrentHashMap<>();
    private static final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Collection<T> findAll() {
        return entities.values();
    }

    @Override
    public T findById(Long id) {
        return entities.get(id);
    }

    @Override
    public void create(T entity) {
        if (entity.getId() == null) {
            entity.setId(idCounter.getAndIncrement());
        }
        entities.put(entity.getId(), entity);
    }

    @Override
    public void update(T entity) {
        Long id = entity.getId();
        if (entities.containsKey(id)) {
            entities.put(id, entity);
        }
    }

    @Override
    public void delete(Long id) {
        entities.remove(id);
    }
}
