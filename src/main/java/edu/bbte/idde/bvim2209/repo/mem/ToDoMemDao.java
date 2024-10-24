package edu.bbte.idde.bvim2209.repo.mem;

import edu.bbte.idde.bvim2209.model.ToDo;
import edu.bbte.idde.bvim2209.repo.ToDoDao;

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
}
