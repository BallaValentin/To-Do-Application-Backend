package edu.bbte.idde.bvim2209.repo;

import edu.bbte.idde.bvim2209.repo.jdbc.JDBCDaoFactory;
import edu.bbte.idde.bvim2209.repo.mem.MemDaoFactory;
import edu.bbte.idde.bvim2209.util.PropertyProvider;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
            String implementationType = PropertyProvider.getProperty("DAO_IMPLEMENTATION");
            if ("jdbc".equals(implementationType)) {
                System.out.println("JDBC");
            } else {
                System.out.println("MEMORY");
            }
            instance = new JDBCDaoFactory();
        }
        return instance;
    }

    public abstract ToDoDao getToDoDao();
}
