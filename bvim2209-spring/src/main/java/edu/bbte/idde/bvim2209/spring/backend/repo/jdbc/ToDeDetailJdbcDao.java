package edu.bbte.idde.bvim2209.spring.backend.repo.jdbc;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDoDetail;
import edu.bbte.idde.bvim2209.spring.backend.repo.ToDoDetailDao;
import lombok.extern.slf4j.Slf4j;
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

    protected ToDeDetailJdbcDao(DataSource dataSource) {
        super(dataSource, null, null, null, null);
    }

    @Override
    protected ToDoDetail mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    protected void setStatementForInsert(PreparedStatement preparedStatement, ToDoDetail entity) throws SQLException {

    }

    @Override
    protected void setStatementForUpdate(PreparedStatement preparedStatement, ToDoDetail entity) throws SQLException {

    }
}
