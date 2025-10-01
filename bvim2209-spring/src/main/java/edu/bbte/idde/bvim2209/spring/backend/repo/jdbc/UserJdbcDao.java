package edu.bbte.idde.bvim2209.spring.backend.repo.jdbc;

import edu.bbte.idde.bvim2209.spring.backend.model.User;
import edu.bbte.idde.bvim2209.spring.backend.repo.UserDao;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class UserJdbcDao extends JdbcDao<User> implements UserDao {

    protected UserJdbcDao(DataSource dataSource) {
        super(dataSource, null, null, null, null);
    }

    @Override
    protected User mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    protected void setStatementForInsert(PreparedStatement preparedStatement, User entity) throws SQLException {

    }

    @Override
    protected void setStatementForUpdate(PreparedStatement preparedStatement, User entity) throws SQLException {

    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Collection<User> findAll(Specification<User> spec) {
        return List.of();
    }
}
