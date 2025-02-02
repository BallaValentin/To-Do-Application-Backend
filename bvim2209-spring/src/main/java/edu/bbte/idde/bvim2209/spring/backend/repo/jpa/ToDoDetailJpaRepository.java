package edu.bbte.idde.bvim2209.spring.backend.repo.jpa;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDoDetail;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface ToDoDetailJpaRepository extends JpaRepository<ToDoDetail, Long> {
    @Modifying
    @Transactional
    @Query("update ToDoDetail td set td.text=:#{#toDoDetail.text} where td.id=:#{#toDoDetail.id}")
    void update(@Param("toDo") ToDoDetail toDoDetail);
}
