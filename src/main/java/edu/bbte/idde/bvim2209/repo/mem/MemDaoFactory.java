package edu.bbte.idde.bvim2209.repo.mem;

import com.sun.tools.javac.Main;
import edu.bbte.idde.bvim2209.repo.ToDoDao;
import edu.bbte.idde.bvim2209.repo.DaoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Az absztract DAO factory egyik konkretizációja.
 * Garantálja, hogy mindegyik entitás elérése
 * egy ugyanazon módszerrel történik.
 */
public class MemDaoFactory extends DaoFactory {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    @Override
    public ToDoDao getToDoDao() {
        logger.info("Fetching ToDao instance of type: " + ToDoMemDao.class.getSimpleName());
        return new ToDoMemDao();
    }
}
