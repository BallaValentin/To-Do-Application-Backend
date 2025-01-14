package edu.bbte.idde.bvim2209.spring.backend.services;

import edu.bbte.idde.bvim2209.spring.backend.model.User;
import edu.bbte.idde.bvim2209.spring.backend.repo.UserDao;
import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.spring.exceptions.InvalidJwtException;
import edu.bbte.idde.bvim2209.spring.exceptions.UnauthorizedException;
import edu.bbte.idde.bvim2209.spring.web.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil = new JwtUtil();

    @Autowired
    public UserService(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserFromToken(String jwtToken) {
        try {
            Jws<Claims> parsedToken = jwtUtil.parseToken(jwtToken);
            Date expirationDate = parsedToken.getBody().getExpiration();
            if (expirationDate.after(new Date())) {
                String username = parsedToken.getBody().getSubject().split("\\|")[0];
                Optional<User> user = userDao.findByUsername(username);
                if (user.isEmpty()) {
                    throw new InvalidJwtException("Invalid JWT token");
                } else {
                    return user.get();
                }
            } else {
                throw new InvalidJwtException("Invalid JWT token");
            }
        } catch (JwtException exception) {
            throw new InvalidJwtException("Invalid JWT token");
        }
    }

    public void registerUser(User user) {
        Optional<User> userWithSameUsername = userDao.findByUsername(user.getUsername());
        if (userWithSameUsername.isPresent()) {
            throw new BadCredentialsException("Username is already taken");
        } else {
            Optional<User> userWithSameEmail = userDao.findByEmail(user.getEmail());
            if (userWithSameEmail.isPresent()) {
                throw new BadCredentialsException("Email is already taken");
            } else {
                String hashedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(hashedPassword);
                userDao.saveAndFlush(user);
            }
        }
    }

    public User loginUser(User loginData) {
        Optional<User> user = userDao.findByUsername(loginData.getUsername());
        if (user.isPresent()) {
            if (passwordEncoder.matches(loginData.getPassword(), user.get().getPassword())) {
                return user.get();
            } else {
                throw new BadCredentialsException("Invalid username or password");
            }
        } else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    public Collection<User> getAllUsers(String jwtToken) {
        User userFromToken = getUserFromToken(jwtToken);
        if ("admin".equals(userFromToken.getRole())) {
            return userDao.findAll();
        } else {
            throw new UnauthorizedException("You are not an admin");
        }
    }

    public User getById(Long id) {
        Optional<User> user = userDao.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new EntityNotFoundException("User not found");
    }

    public void deleteUser(User user, String jwtToken) {
        User userFromToken = getUserFromToken(jwtToken);
        if ("admin".equals(userFromToken.getRole())) {
            userDao.deleteById(user.getId());
        } else {
            throw new UnauthorizedException("You are not an admin");
        }
    }
}
