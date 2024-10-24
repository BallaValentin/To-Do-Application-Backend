package edu.bbte.idde.bvim2209.repo;

import edu.bbte.idde.bvim2209.model.ToDo;

import java.util.Collection;



/**
 * Egy bizonyos entitásra specifikus CRUD műveletek.
 */

    public interface ToDoDao extends Dao<ToDo> {

        /**
         * Szűrt entitáslista visszatérítése.
         */
        Collection<ToDo> findByTitle(String title);

        // további blogposzt-specifikus CRUD műveletek...
    }


