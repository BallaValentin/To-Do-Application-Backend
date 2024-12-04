package edu.bbte.idde.bvim2209.spring.backend.repo.jdbc;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.repo.ToDoDao;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jdbc")
public class ToDoJdbcDao extends JdbcDao<ToDo> implements ToDoDao {
}
