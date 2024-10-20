package edu.bbte.idde.bvim2209.repo;

import edu.bbte.idde.bvim2209.repo.mem.MemDaoFactory;

/**
 * Általános factory
 */
public abstract class DaoFactory {
    // singleton lazy loading
    private static DaoFactory instance;

    /**
     * Kérünk egy példányt - itt dől el az adatelérési módszer.
     */
    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            instance = new MemDaoFactory();
        }
        return instance;
    }

    public abstract ToDoDao getToDoDao();
}
