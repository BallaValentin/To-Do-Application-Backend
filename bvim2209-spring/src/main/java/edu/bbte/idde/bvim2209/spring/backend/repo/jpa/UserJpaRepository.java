package edu.bbte.idde.bvim2209.spring.backend.repo.jpa;

import edu.bbte.idde.bvim2209.spring.backend.model.User;
import edu.bbte.idde.bvim2209.spring.backend.repo.UserDao;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface UserJpaRepository extends JpaRepository<User, Long>, UserDao {
    @Override
    @Modifying
    @Transactional
    @Query("update User u "
            + "set u.username=:#{#user.username}, "
            + "u.fullname=:#{#user.fullname}, "
            + "u.email=:#{#user.email}, "
            + "u.password=:#{#user.password}, "
            + "u.role=:#{#user.role} "
            + "where u.id=:#{#user.id}")
    void update(@Param("user") User user);
}
