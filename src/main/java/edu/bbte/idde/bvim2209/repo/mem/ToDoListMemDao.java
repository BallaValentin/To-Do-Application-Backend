package edu.bbte.idde.bvim2209.repo.mem;

import edu.bbte.idde.bvim2209.model.ToDoList;
import edu.bbte.idde.bvim2209.repo.ToDoListDao;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Teljes DAO megvalósítás egy entitásra:
 * - örökli a BlogPostDao-t az érintett entitás miatt
 * - örökli a MemDao-t az elérési mód miatt
 */
public class ToDoListMemDao extends MemDao<ToDoList> implements ToDoListDao {

    @Override
    public Collection<ToDoList> findByTitle(String title) {
        return entities.values().stream()
                .filter(toDoList -> toDoList.getTitle().equals(title))
                .collect(Collectors.toList());
    }
}
