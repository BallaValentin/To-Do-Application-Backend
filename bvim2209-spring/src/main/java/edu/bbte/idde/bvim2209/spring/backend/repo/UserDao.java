package edu.bbte.idde.bvim2209.spring.backend.repo;

import edu.bbte.idde.bvim2209.spring.backend.model.User;

import java.util.Optional;

public interface UserDao extends Dao<User> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
