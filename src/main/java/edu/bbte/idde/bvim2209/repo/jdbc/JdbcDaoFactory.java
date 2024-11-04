package edu.bbte.idde.bvim2209.repo.jdbc;

import edu.bbte.idde.bvim2209.repo.DaoFactory;
import edu.bbte.idde.bvim2209.repo.ToDoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcDaoFactory extends DaoFactory {
    private static final Logger logger = LoggerFactory.getLogger(JdbcDaoFactory.class);

    private ToDoDao instance;

    @Override
    public synchronized ToDoDao getToDoDao() {
        logger.info("Fetching ToDao instance of type: " + ToDoJdbcDao.class.getSimpleName());
        if (instance == null) {
            instance = new ToDoJdbcDao();
        }
        return instance;
    }
}
