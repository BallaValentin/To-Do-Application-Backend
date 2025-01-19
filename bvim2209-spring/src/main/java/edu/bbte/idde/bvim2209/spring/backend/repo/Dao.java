package edu.bbte.idde.bvim2209.spring.backend.repo;

import edu.bbte.idde.bvim2209.spring.backend.model.BaseEntity;
import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Optional;

public interface Dao<T extends BaseEntity> {
    Page<T> findAll(Pageable pageable);

    Optional<T> findById(Long id);

    T saveAndFlush(T entity) throws IllegalArgumentException;

    void update(T entity) throws EntityNotFoundException;

    void deleteById(Long id) throws EntityNotFoundException;
}
