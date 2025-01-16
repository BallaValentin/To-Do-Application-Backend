package edu.bbte.idde.bvim2209.spring.backend.repo.jdbc;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDoDetail;
import edu.bbte.idde.bvim2209.spring.backend.repo.ToDoDetailDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
@Profile("jdbc")
@Slf4j
public class ToDeDetailJdbcDao extends JdbcDao<ToDoDetail> implements ToDoDetailDao {
    private final DataSource dataSource;

    @Autowired
    public ToDeDetailJdbcDao(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }

    @Override
    protected ToDoDetail mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    protected void setStatementForInsert(PreparedStatement preparedStatement, ToDoDetail entity) throws SQLException {

    }

    @Override
    protected void setStatementForInsertWithId(
            PreparedStatement preparedStatement, ToDoDetail entity) throws SQLException {

    }

    @Override
    protected void setStatementForUpdate(PreparedStatement preparedStatement, ToDoDetail entity) throws SQLException {

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
}
