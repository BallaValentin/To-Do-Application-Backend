package edu.bbte.idde.bvim2209.spring.backend.repo.mem;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDoDetail;
import edu.bbte.idde.bvim2209.spring.backend.repo.ToDoDetailDao;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@Profile("!jdbc && !jpa")
public class ToDoDetailMemDao extends MemDao<ToDoDetail> implements ToDoDetailDao {

    @Override
    public Page<ToDoDetail> findByToDoId(Long id, Pageable pageable) {
        return null;
    }
}
