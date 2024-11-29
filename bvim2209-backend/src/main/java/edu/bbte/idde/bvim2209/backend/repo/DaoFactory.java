package edu.bbte.idde.bvim2209.backend.repo;

import edu.bbte.idde.bvim2209.backend.conf.ConfigurationFactory;
import edu.bbte.idde.bvim2209.backend.conf.JdbcConfiguration;
import edu.bbte.idde.bvim2209.backend.repo.jdbc.JdbcDaoFactory;
import edu.bbte.idde.bvim2209.backend.repo.mem.MemDaoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Általános factory
 */
public abstract class DaoFactory {
    // singleton lazy loading
    private static DaoFactory instance;
    private static final Logger logger = LoggerFactory.getLogger(DaoFactory.class);

    /**
     * Kérünk egy példányt - itt dől el az adatelérési módszer.
     */
    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            Object activeConfiguration = ConfigurationFactory.getActiveProfileConfig();
            if (activeConfiguration instanceof JdbcConfiguration) {
                logger.info("Creating instance of JdbcDaoFactory...");
                instance = new JdbcDaoFactory();
            } else {
                logger.info("Creating new instance of MemDaoFactory");
                instance = new MemDaoFactory();
            }
        }
        return instance;
    }

    public abstract ToDoDao getToDoDao();
}
