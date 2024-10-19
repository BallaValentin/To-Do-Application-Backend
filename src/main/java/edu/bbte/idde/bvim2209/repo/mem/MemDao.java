package edu.bbte.idde.bvim2209.repo.mem;

import edu.bbte.idde.bvim2209.model.BaseEntity;
import edu.bbte.idde.bvim2209.repo.Dao;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * CRUD műveletek implementációja egy elérési móddal.
 * Itt, a példa kedvéért: memóriában tárolt entitások.
 */
public abstract class MemDao<T extends BaseEntity> implements Dao<T> {

    protected Map<Long, T> entities = new ConcurrentHashMap<>();

    @Override
    public Collection<T> findAll() {
        return entities.values();
    }

    @Override
    public void create(T entity) {
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
    public void delete(T entity) {
        entities.remove(entity.getId());
    }

    // további itt megvalósítható CRUD műveletek
}
