package edu.bbte.idde.bvim2209.spring.backend.repo.jdbc;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.repo.ToDoDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@Profile("jdbc")
@Slf4j
public class ToDoJdbcDao extends JdbcDao<ToDo> implements ToDoDao {
    private final DataSource dataSource;

    @Autowired
    public ToDoJdbcDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected ToDo mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        log.info("Mapping ResultSet to ToDo entity.");
        ToDo toDo = new ToDo();
        toDo.setId(resultSet.getLong("ID"));
        toDo.setTitle(resultSet.getString("Title"));
        toDo.setDescription(resultSet.getString("Description"));
        toDo.setDueDate(resultSet.getDate("DueDate"));
        toDo.setLevelOfImportance(resultSet.getInt("ImportanceLevel"));
        return toDo;
    }

    @Override
    protected void setStatementForInsert(PreparedStatement preparedStatement, ToDo entity) throws SQLException {
        log.info("Setting parameters for PreparedStatement to insert ToDo entity");
        preparedStatement.setString(1, entity.getTitle());
        preparedStatement.setString(2, entity.getDescription());
        preparedStatement.setDate(3, new java.sql.Date(entity.getDueDate().getTime()));
        preparedStatement.setInt(4, entity.getLevelOfImportance());
    }

    @Override
    protected void setStatementForUpdate(PreparedStatement preparedStatement, ToDo entity) throws SQLException {
        log.info("Setting parameters for PreparedStatement to update ToDo entity");
        preparedStatement.setString(1, entity.getTitle());
        preparedStatement.setString(2, entity.getDescription());
        preparedStatement.setDate(3, new java.sql.Date(entity.getDueDate().getTime()));
        preparedStatement.setInt(4, entity.getLevelOfImportance());
    }

    @Override
    protected PreparedStatement prepareStatementForFindAll() throws SQLException {
        String query = "SELECT * FROM ToDo";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement(query);
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForInsert() throws SQLException {
        String query = "INSERT INTO ToDo (Title, Description, DueDate, ImportanceLevel) VALUES (?, ?, ?, ?)";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForFindById(Long id) throws SQLException {
        String query = "SELECT * FROM ToDo WHERE ID=?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement(query);
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForDeleteById(Long id) throws SQLException {
        String query = "DELETE FROM ToDo WHERE ID=?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement(query);
        return preparedStatement;
    }

    @Override
    protected PreparedStatement prepareStatementForUpdate() throws SQLException {
        String query = "UPDATE ToDo SET Title=?, Description=?, DueDate=?, ImportanceLevel=? WHERE ID=?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement(query);
        return preparedStatement;
    }

    @Override
    protected Integer getPrimaryKeyIndex() {
        return 1;
    }

    @Override
    protected Integer getNumberOfColumnsToUpdate() {
        return 4;
    }
}
