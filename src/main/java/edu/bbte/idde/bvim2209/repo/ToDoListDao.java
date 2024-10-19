package edu.bbte.idde.bvim2209.repo;

import edu.bbte.idde.bvim2209.model.ToDoList;

import java.util.Collection;



/**
 * Egy bizonyos entitásra specifikus CRUD műveletek.
 */

    public interface ToDoListDao extends Dao<ToDoList> {

        /**
         * Szűrt entitáslista visszatérítése.
         */
        Collection<ToDoList> findByTitle(String author);

        // további blogposzt-specifikus CRUD műveletek...
    }


