package edu.bbte.idde.bvim2209.repo.jdbc;

import edu.bbte.idde.bvim2209.model.ToDo;
import edu.bbte.idde.bvim2209.repo.ToDoDao;

import java.util.Collection;

public class ToDoJDBCDao extends JDBCDao<ToDo> implements ToDoDao {
    @Override
    public Collection<ToDo> findByTitle(String title) {
        return null;
    }
}
