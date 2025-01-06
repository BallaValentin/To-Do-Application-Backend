package edu.bbte.idde.bvim2209.spring.backend.services;

import edu.bbte.idde.bvim2209.spring.backend.model.User;
import edu.bbte.idde.bvim2209.spring.backend.repo.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
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
}
