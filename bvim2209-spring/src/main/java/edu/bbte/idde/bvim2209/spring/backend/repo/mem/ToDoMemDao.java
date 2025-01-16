package edu.bbte.idde.bvim2209.spring.backend.repo.mem;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.repo.ToDoDao;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
@Profile("!jdbc && !jpa")
public class ToDoMemDao extends MemDao<ToDo> implements ToDoDao {
    @Override
    public Collection<ToDo> findByLevelOfImportance(Integer levelOfImportance) {
        return entities
                .values()
                .stream()
                .filter(e ->
                        Objects.equals(e.getLevelOfImportance(), levelOfImportance))
                .collect(Collectors.toList());
    }
}
