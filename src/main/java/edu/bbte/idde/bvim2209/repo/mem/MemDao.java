package edu.bbte.idde.bvim2209.repo.mem;

import edu.bbte.idde.bvim2209.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.model.BaseEntity;
import edu.bbte.idde.bvim2209.repo.Dao;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * CRUD műveletek implementációja egy elérési móddal.
 * Itt, a példa kedvéért: memóriában tárolt entitások.
 */
public abstract class MemDao<T extends BaseEntity> implements Dao<T> {

    protected Map<Long, T> entities = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Collection<T> findAll() {
        return entities.values();
    }

    @Override
    public void create(T entity) throws EntityNotFoundException {
        if (entity.getId() == null) {
            entity.setId(idCounter.getAndIncrement());
        }
        entities.put(entity.getId(), entity);
    }

    @Override
    public void update(T entity) throws EntityNotFoundException {
        Long id = entity.getId();
        if (entities.containsKey(id)) {
            entities.put(id, entity);
        } else {
            throw new EntityNotFoundException("To Do with the given ID does not exists");
        }
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException{
        if(entities.containsKey(id)) {
            entities.remove(id);
        } else {
            throw new EntityNotFoundException("To Do with the given ID does not exists");
        }
    }

    // további itt megvalósítható CRUD műveletek
}
