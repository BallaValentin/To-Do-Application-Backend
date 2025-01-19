package edu.bbte.idde.bvim2209.spring.backend.services;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.model.ToDoDetail;
import edu.bbte.idde.bvim2209.spring.backend.repo.ToDoDao;
import edu.bbte.idde.bvim2209.spring.backend.repo.ToDoDetailDao;
import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ToDoServiceImpl implements ToDoService {

    private final ToDoDao toDoDao;
    private final ToDoDetailDao toDoDetailDao;

    @Autowired
    public ToDoServiceImpl(ToDoDao toDoDao, ToDoDetailDao toDoDetailDao) {
        this.toDoDao = toDoDao;
        this.toDoDetailDao = toDoDetailDao;
    }

    @Override
    public void createToDo(ToDo toDo) throws IllegalArgumentException {
        toDoDao.saveAndFlush(toDo);
    }

    @Override
    public void updateToDo(ToDo toDo) throws EntityNotFoundException, IllegalArgumentException {
        validateId(toDo.getId());
        toDoDao.update(toDo);
    }

    @Override
    public void deleteToDo(Long id) throws EntityNotFoundException {
        validateId(id);
        toDoDao.deleteById(id);
    }

    @Override
    public ToDo getById(Long id) throws EntityNotFoundException {
        Optional<ToDo> toDo = toDoDao.findById(id);
        if (toDo.isEmpty()) {
            throw new EntityNotFoundException("ToDo with id " + id + " not found");
        }
        return toDo.get();
    }

    private void validateId(Long id) {
        getById(id);
    }

    private Pageable getPageable(Integer page, Integer size, String sortBy, String order) {
        Sort sort = Sort.by(Sort.Order.by(sortBy));
        if ("desc".equalsIgnoreCase(order)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        return PageRequest.of(page, size, sort);
    }

    @Override
    public Page<ToDo> findAll(Integer page, Integer size, String sortBy, String order) {
        Pageable pageable = getPageable(page, size, sortBy, order);
        return toDoDao.findAll(pageable);
    }

    @Override
    public Page<ToDo> findByImportance(
            Integer importanceLevel, Integer page, Integer size, String sortBy, String order
    ) {
        Pageable pageable = getPageable(page, size, sortBy, order);
        return toDoDao.findByLevelOfImportance(importanceLevel, pageable);
    }

    @Override
    public Page<ToDoDetail> getDetails(Long id, Integer page, Integer size, String sortBy, String order) {
        Pageable pageable = getPageable(page, size, sortBy, order);
        getById(id);
        return toDoDetailDao.findByToDoId(id, pageable);
    }

    @Override
    public void addDetailToToDo(Long id, ToDoDetail toDoDetail) throws EntityNotFoundException {
        ToDo toDo = getById(id);
        toDo.getDetails().add(toDoDetail);
        toDoDetail.setToDo(toDo);
        toDoDetailDao.saveAndFlush(toDoDetail);
        toDoDao.update(toDo);
    }

    @Override
    public void deleteDetailById(Long toDoId, Long toDoDetailId) throws EntityNotFoundException {
        ToDo toDo = getById(toDoId);
        Optional<ToDoDetail> toDoDetail = toDoDetailDao.findById(toDoDetailId);
        if (toDoDetail.isEmpty()) {
            throw new EntityNotFoundException("ToDoDetail with id " + toDoDetailId + " not found");
        } else {
            toDo.getDetails().remove(toDoDetail.get());
            toDoDao.update(toDo);
            toDoDetailDao.deleteById(toDoDetailId);
        }
    }
}
