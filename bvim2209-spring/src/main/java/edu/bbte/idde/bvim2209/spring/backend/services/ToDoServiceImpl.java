package edu.bbte.idde.bvim2209.spring.backend.services;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.model.ToDoDetail;
import edu.bbte.idde.bvim2209.spring.backend.model.User;
import edu.bbte.idde.bvim2209.spring.backend.repo.ToDoDao;
import edu.bbte.idde.bvim2209.spring.backend.repo.ToDoDetailDao;
import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.spring.exceptions.AuthenticationException;
import edu.bbte.idde.bvim2209.spring.exceptions.UnauthorizedException;
import edu.bbte.idde.bvim2209.spring.backend.util.ToDoServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Service
public class ToDoServiceImpl implements ToDoService {

    private final ToDoDao toDoDao;
    private final ToDoDetailDao toDoDetailDao;
    private final ToDoServiceUtil toDoServiceUtil = new ToDoServiceUtil();
    private final UserService userService;

    @Autowired
    public ToDoServiceImpl(
            ToDoDao toDoDao, ToDoDetailDao toDoDetailDao, UserService userService) {
        this.userService = userService;
        this.toDoDao = toDoDao;
        this.toDoDetailDao = toDoDetailDao;
    }

    @Override
    public void createToDo(ToDo toDo, String jwtToken) throws
            IllegalArgumentException, AuthenticationException, EntityNotFoundException {
        toDoServiceUtil.validateToDo(toDo);
        User user = userService.getUserFromToken(jwtToken);
        toDo.setUser(user);
        toDoDao.saveAndFlush(toDo);
    }

    @Override
    public void updateToDo(ToDo toDo, String jwtToken) throws
            EntityNotFoundException, IllegalArgumentException, AuthenticationException {
        validateId(toDo.getId());
        toDoServiceUtil.validateToDo(toDo);
        Optional<ToDo> toDoToUpdate = toDoDao.findById(toDo.getId());
        if (toDoToUpdate.isPresent()) {
            User user = userService.getUserFromToken(jwtToken);
            if (Objects.equals(toDoToUpdate.get().getUser().getId(), user.getId())) {
                toDoDao.update(toDo);
            } else {
                throw new UnauthorizedException("You are not allowed to update this entity");
            }
        }
    }

    @Override
    public void deleteToDo(Long id, String jwtToken) throws
            EntityNotFoundException, AuthenticationException {
        validateId(id);
        Optional<ToDo> toDo = toDoDao.findById(id);
        if (toDo.isPresent()) {
            User user = userService.getUserFromToken(jwtToken);
            if (Objects.equals(toDo.get().getUser().getId(), user.getId())) {
                toDoDao.deleteById(id);
            } else {
                throw new UnauthorizedException("You are not allowed to delete this entity");
            }
        }
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

    @Override
    public Collection<ToDo> findAll() {
        return toDoDao.findAll();
    }

    @Override
    public Collection<ToDo> findByImportance(Integer levelOfImportance) {
        return toDoDao.findByLevelOfImportance(levelOfImportance);
    }

    @Override
    public Collection<ToDoDetail> getDetails(Long id) {
        ToDo toDo = getById(id);
        return toDo.getDetails();
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
