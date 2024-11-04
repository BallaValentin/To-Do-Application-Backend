package edu.bbte.idde.bvim2209.repo.jdbc;

import edu.bbte.idde.bvim2209.model.ToDo;
import edu.bbte.idde.bvim2209.repo.ToDoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;

public class ToDoJdbcDao extends JdbcDao<ToDo> implements ToDoDao {

    private static final Logger logger = LoggerFactory.getLogger(ToDoJdbcDao.class);

    @Override
    public Collection<ToDo> findByTitle(String title) {
        return Collections.emptyList();
    }

    @Override
    protected ToDo mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        logger.info("Mapping ResultSet to ToDo entity.");
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
        logger.info("Setting parameters for PreparedStatement to insert ToDo entity");
        preparedStatement.setString(1, entity.getTitle());
        preparedStatement.setString(2, entity.getDescription());
        preparedStatement.setDate(3, new java.sql.Date(entity.getDueDate().getTime()));
        preparedStatement.setInt(4, entity.getLevelOfImportance());
    }

    @Override
    protected void setStatementForUpdate(PreparedStatement preparedStatement, ToDo entity) throws SQLException {
        logger.info("Setting parameters for PreparedStatement to update ToDo entity");
        preparedStatement.setString(1, entity.getTitle());
        preparedStatement.setString(2, entity.getDescription());
        preparedStatement.setDate(3, new java.sql.Date(entity.getDueDate().getTime()));
        preparedStatement.setInt(4, entity.getLevelOfImportance());
    }
}
