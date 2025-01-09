package edu.bbte.idde.bvim2209.spring.backend.services;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.model.ToDoDetail;
import edu.bbte.idde.bvim2209.spring.backend.model.User;
import edu.bbte.idde.bvim2209.spring.backend.repo.ToDoDao;
import edu.bbte.idde.bvim2209.spring.backend.repo.ToDoDetailDao;
import edu.bbte.idde.bvim2209.spring.backend.repo.UserDao;
import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.spring.exceptions.InvalidJwtException;
import edu.bbte.idde.bvim2209.spring.web.util.JwtUtil;
import edu.bbte.idde.bvim2209.spring.web.util.ToDoServiceUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
public class ToDoServiceImpl implements ToDoService {

    private final ToDoDao toDoDao;
    private final ToDoDetailDao toDoDetailDao;
    private final UserDao userDao;
    private final JwtUtil jwtUtil = new JwtUtil();
    private final ToDoServiceUtil toDoServiceUtil = new ToDoServiceUtil();

    @Autowired
    public ToDoServiceImpl(ToDoDao toDoDao, ToDoDetailDao toDoDetailDao, UserDao userDao) {
        this.userDao = userDao;
        this.toDoDao = toDoDao;
        this.toDoDetailDao = toDoDetailDao;
    }

    @Override
    public void createToDo(ToDo toDo, String jwtToken) throws
            IllegalArgumentException, InvalidJwtException, EntityNotFoundException {
        toDoServiceUtil.validateToDo(toDo);
        validateToken(toDo, jwtToken);
        toDoDao.saveAndFlush(toDo);
    }

    @Override
    public void updateToDo(ToDo toDo, String jwtToken) throws
            EntityNotFoundException, IllegalArgumentException, InvalidJwtException {
        validateId(toDo.getId());
        toDoServiceUtil.validateToDo(toDo);
        validateToken(toDo, jwtToken);
        toDoDao.update(toDo);
    }

    @Override
    public void deleteToDo(Long id, String jwtToken) throws
            EntityNotFoundException, InvalidJwtException {
        validateId(id);
        Optional<ToDo> toDo = toDoDao.findById(id);
        toDo.ifPresent(todo -> validateToken(todo, jwtToken));
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

    private void validateToken(ToDo toDo, String jwtToken) {
        try {
            Jws<Claims> parsedToken = jwtUtil.parseToken(jwtToken);
            Date expirationDate = parsedToken.getBody().getExpiration();
            if (expirationDate.after(new Date())) {
                String username = parsedToken.getBody().getSubject();
                Optional<User> user = userDao.findByUsername(username);
                if (user.isEmpty()) {
                    throw new InvalidJwtException("Invalid JWT token");
                } else {
                    toDo.setUser(user.get());
                }
            } else {
                throw new InvalidJwtException("Invalid JWT token");
            }
        } catch (JwtException exception) {
            throw new InvalidJwtException("Invalid JWT token");
        }
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
