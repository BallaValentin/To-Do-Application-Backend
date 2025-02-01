package edu.bbte.idde.bvim2209.backend.repo.mem;

import edu.bbte.idde.bvim2209.backend.model.ToDo;
import edu.bbte.idde.bvim2209.backend.repo.ToDoDao;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Teljes DAO megvalósítás egy entitásra:
 * - örökli a BlogPostDao-t az érintett entitás miatt
 * - örökli a MemDao-t az elérési mód miatt
 */
public class ToDoMemDao extends MemDao<ToDo> implements ToDoDao {

    @Override
    public Collection<ToDo> findByTitle(String title) {
        return entities.values().stream()
                .filter(toDo -> toDo.getTitle().equals(title))
                .collect(Collectors.toList());
    }

    @Override
    public Boolean getLogQueries() {
        return false;
    }

    @Override
    public Boolean getLogUpdates() {
        return false;
    }

    @Override
    public Integer getLogQueriesCount() {
        return 0;
    }

    @Override
    public Integer getLogUpdatesCount() {
        return 0;
    }
}
