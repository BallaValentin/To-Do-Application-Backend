package edu.bbte.idde.bvim2209.spring.backend.repo.mem;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.repo.ToDoDao;
import org.springframework.stereotype.Repository;

@Repository
public class ToDoMemDao extends MemDao<ToDo> implements ToDoDao {
}
