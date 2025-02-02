package edu.bbte.idde.bvim2209.backend.repo;

import edu.bbte.idde.bvim2209.backend.model.ToDo;

import java.util.Collection;


/**
 * Egy bizonyos entitásra specifikus CRUD műveletek.
 */


public interface ToDoDao extends Dao<ToDo> {

    /**
     * Szűrt entitáslista visszatérítése.
     */
    Collection<ToDo> findByPriority(Integer priority);

    Collection<ToDo> findAllByPriorityBetweenInterval(Integer min, Integer max);
    // további blogposzt-specifikus CRUD műveletek...
}


