package edu.bbte.idde.bvim2209.spring.backend.repo.jpa;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.repo.ToDoDao;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
@Profile("jpa")
public interface ToDoJpaRepository extends
        ToDoDao, JpaRepository<ToDo, Long>, JpaSpecificationExecutor<ToDo> {
    @Override
    @Modifying
    @Transactional
    @Query("update ToDo t "
            + "set t.title=:#{#toDo.title}, "
            + "t.description=:#{#toDo.description}, "
            + "t.dueDate=:#{#toDo.dueDate}, "
            + "t.levelOfImportance=:#{#toDo.levelOfImportance} "
            + "where t.id=:#{#toDo.id}")
    void update(@Param("toDo") ToDo toDo);
}
