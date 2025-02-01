package edu.bbte.idde.bvim2209.backend.repo.mem;

import edu.bbte.idde.bvim2209.backend.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.backend.model.BaseEntity;
import edu.bbte.idde.bvim2209.backend.repo.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * CRUD műveletek implementációja egy elérési móddal.
 * Itt, a példa kedvéért: memóriában tárolt entitások.
 */
public abstract class MemDao<T extends BaseEntity> implements Dao<T> {
    private static final Logger logger = LoggerFactory.getLogger(MemDao.class);

    protected transient Map<Long, T> entities = new ConcurrentHashMap<>();
    private static final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Collection<T> findAll() {
        logger.info("Try to fetch all entities from memory");
        return entities.values();
    }

    @Override
    public void create(T entity) throws IllegalArgumentException {
        logger.info("Trying to insert new entity to memory");
        if (entity.getId() == null) {
            entity.setId(idCounter.getAndIncrement());
        } else if (entities.containsKey(entity.getId())) {
            logger.error("Entity with ID {} already exists", entity.getId());
            throw new IllegalArgumentException("Entity with ID " + entity.getId() + " already exists");
        }
        entities.put(entity.getId(), entity);
        logger.info("New entity has been successfully added to memory");
    }

    @Override
    public void addEntities(List<T> entities) {
        entities.forEach(this::create);
    }

    @Override
    public T findById(Long id) throws EntityNotFoundException {
        logger.info("Trying to find entity with ID {} in memory", id);
        T entity = entities.get(id);
        if (entity == null) {
            logger.error("Entity with ID {} is not in the memory", id);
            throw new EntityNotFoundException("Entity with ID " + id + " not found in memory");
        }
        logger.info("Entity with ID {} has been successfully found in memory", id);
        return entity;
    }

    @Override
    public void update(T entity) throws EntityNotFoundException {
        Long id = entity.getId();
        logger.info("Trying to update entity with ID {} in memory", id);
        if (entities.containsKey(id)) {
            entities.put(id, entity);
        } else {
            logger.error("Failed to update entity with ID {} in memory", id);
            throw new EntityNotFoundException("To Do with the given ID does not exists");
        }
        logger.info("Entity with ID {} has been successfully updated in memory", id);
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        logger.info("Trying to delete entity with ID {} from memory", id);
        if (entities.containsKey(id)) {
            entities.remove(id);
        } else {
            logger.error("Failed to delete entity with ID {} from memory", id);
            throw new EntityNotFoundException("To Do with the given ID does not exists");
        }
        logger.info("Entity with ID {} has been successfully deleted from memory", id);
    }

    // további itt megvalósítható CRUD műveletek
}
