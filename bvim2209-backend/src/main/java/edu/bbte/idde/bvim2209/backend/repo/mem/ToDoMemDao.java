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
    public Collection<ToDo> findByPriority(Integer priority) {
        return entities.values().stream()
                .filter(toDo -> toDo.getLevelOfImportance().equals(priority))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ToDo> findAllByPriorityBetweenInterval(Integer min, Integer max) {
        return entities.values().stream()
                .filter(toDo -> toDo.getLevelOfImportance() >= min
                        && toDo.getLevelOfImportance() <= max)
                .collect(Collectors.toList());
    }
}
