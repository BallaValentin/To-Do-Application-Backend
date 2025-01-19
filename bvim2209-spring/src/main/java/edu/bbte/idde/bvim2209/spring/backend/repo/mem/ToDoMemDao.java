package edu.bbte.idde.bvim2209.spring.backend.repo.mem;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.repo.ToDoDao;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@Profile("!jdbc && !jpa")
public class ToDoMemDao extends MemDao<ToDo> implements ToDoDao {

    @Override
    public Page<ToDo> findByLevelOfImportance(Integer levelOfImportance, Pageable pageable) {
        return null;
    }
}
