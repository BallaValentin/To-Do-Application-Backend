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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
        toDo.setCreatedBy(user.getUsername());
        toDoDao.saveAndFlush(toDo);
    }

    @Override
    public void updateToDo(ToDo toDoUpdates, String jwtToken) throws
            EntityNotFoundException, IllegalArgumentException, AuthenticationException {
        toDoServiceUtil.validateToDo(toDoUpdates);
        getById(toDoUpdates.getId(), jwtToken);
        toDoDao.update(toDoUpdates);
    }

    @Override
    public void deleteToDo(Long id, String jwtToken) throws
            EntityNotFoundException, AuthenticationException {
        getById(id, jwtToken);
        toDoDao.deleteById(id);
    }

    @Override
    public ToDo getById(Long id, String jwtToken) throws EntityNotFoundException {
        User user = userService.getUserFromToken(jwtToken);
        Optional<ToDo> toDo = toDoDao.findByIdAndUser(id, user);
        if (toDo.isEmpty()) {
            throw new EntityNotFoundException("ToDo with id " + id + " not found");
        }
        return toDo.get();
    }

    @Override
    public Collection<ToDo> findAll(Specification<ToDo> toDoSpecification, String jwtToken) {
        return toDoDao.findAll(toDoSpecification);
    }

    @Override
    public Collection<ToDoDetail> getDetails(Long id, String jwtToken) {
        ToDo toDo = getById(id, jwtToken);
        return toDo.getDetails();
    }

    @Override
    public void addDetailToToDo(Long id, ToDoDetail toDoDetail, String jwtToken)
            throws EntityNotFoundException {
        ToDo toDo = getById(id, jwtToken);
        User user = userService.getUserFromToken(jwtToken);
        if (user.equals(toDo.getUser())) {
            toDo.getDetails().add(toDoDetail);
            toDoDetail.setToDo(toDo);
            toDoDetailDao.saveAndFlush(toDoDetail);
            toDoDao.update(toDo);
        } else {
            throw new UnauthorizedException("You are not allowed to update this entity");
        }
    }

    @Override
    public void deleteDetailById(Long toDoId, Long toDoDetailId, String jwtToken)
            throws EntityNotFoundException {
        ToDo toDo = getById(toDoId, jwtToken);
        Optional<ToDoDetail> toDoDetail = toDoDetailDao.findById(toDoDetailId);
        if (toDoDetail.isEmpty()) {
            throw new EntityNotFoundException("ToDoDetail with id " + toDoDetailId + " not found");
        } else {
            User user = userService.getUserFromToken(jwtToken);
            if (user.equals(toDo.getUser())) {
                toDo.getDetails().remove(toDoDetail.get());
                toDoDao.update(toDo);
                toDoDetailDao.deleteById(toDoDetailId);
            } else {
                throw new UnauthorizedException("You are not allowed to delete this entity");
            }
        }
    }
}
