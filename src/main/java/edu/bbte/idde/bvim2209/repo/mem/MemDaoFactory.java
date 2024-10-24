package edu.bbte.idde.bvim2209.repo.mem;

import edu.bbte.idde.bvim2209.repo.ToDoDao;
import edu.bbte.idde.bvim2209.repo.DaoFactory;


/**
 * Az absztract DAO factory egyik konkretizációja.
 * Garantálja, hogy mindegyik entitás elérése
 * egy ugyanazon módszerrel történik.
 */
public class MemDaoFactory extends DaoFactory {
    @Override
    public ToDoDao getToDoDao() {
        return new ToDoMemDao();
    }
}
