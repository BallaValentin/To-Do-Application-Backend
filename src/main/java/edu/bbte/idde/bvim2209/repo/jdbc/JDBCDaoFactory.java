package edu.bbte.idde.bvim2209.repo.jdbc;

import edu.bbte.idde.bvim2209.repo.DaoFactory;
import edu.bbte.idde.bvim2209.repo.ToDoDao;

public class JDBCDaoFactory extends DaoFactory {
    @Override
    public ToDoDao getToDoDao() {
        return new ToDoJDBCDao();
    }
}
