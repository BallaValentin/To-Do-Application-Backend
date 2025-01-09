package edu.bbte.idde.bvim2209.spring.backend.repo.jdbc;

import edu.bbte.idde.bvim2209.spring.backend.model.User;
import edu.bbte.idde.bvim2209.spring.backend.repo.UserDao;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class UserJdbcDao extends JdbcDao<User> implements UserDao {
    @Override
    protected User mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    protected void setStatementForInsert(PreparedStatement preparedStatement, User entity) throws SQLException {

    }

    @Override
    protected void setStatementForInsertWithId(PreparedStatement preparedStatement, User entity) throws SQLException {

    }

    @Override
    protected void setStatementForUpdate(PreparedStatement preparedStatement, User entity) throws SQLException {

    }

    @Override
    protected PreparedStatement prepareStatementForFindAll() throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement prepareStatementForInsert() throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement prepareStatementForInsertWithId() throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement prepareStatementForFindById(Long id) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement prepareStatementForDeleteById(Long id) throws SQLException {
        return null;
    }

    @Override
    protected PreparedStatement prepareStatementForUpdate() throws SQLException {
        return null;
    }

    @Override
    protected Integer getPrimaryKeyIndex() {
        return 0;
    }

    @Override
    protected Integer getNumberOfColumnsToUpdate() {
        return 0;
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
    public Optional<User> findByJwtToken(String jwtToken) {
        return Optional.empty();
    }
}
