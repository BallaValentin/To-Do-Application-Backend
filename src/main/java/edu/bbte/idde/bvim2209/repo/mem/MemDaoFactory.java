package edu.bbte.idde.bvim2209.repo.mem;

import edu.bbte.idde.bvim2209.repo.ToDoListDao;
import edu.bbte.idde.bvim2209.repo.DaoFactory;


/**
 * Az absztract DAO factory egyik konkretizációja.
 * Garantálja, hogy mindegyik entitás elérése
 * egy ugyanazon módszerrel történik.
 */
public class MemDaoFactory extends DaoFactory {

    @Override
    public ToDoListDao getToDoListDao() {

        // mindig új példányt térítünk vissza
        // itt lehetne implementálni a
        // singleton vagy pooling mintákat is
        return new ToDoListMemDao();
    }
}
