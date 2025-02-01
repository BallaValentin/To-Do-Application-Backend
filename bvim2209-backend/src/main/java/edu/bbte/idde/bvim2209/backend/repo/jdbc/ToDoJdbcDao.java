package edu.bbte.idde.bvim2209.backend.repo.jdbc;

import edu.bbte.idde.bvim2209.backend.model.ToDo;
import edu.bbte.idde.bvim2209.backend.repo.ToDoDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class ToDoJdbcDao extends JdbcDao<ToDo> implements ToDoDao {

    private static final Logger logger = LoggerFactory.getLogger(ToDoJdbcDao.class);

    protected ToDoJdbcDao() {
        super("ToDo", "ID", 1,
                new ArrayList<>(Arrays.asList("Title", "Description", "DueDate", "ImportanceLevel")
                ));
    }

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
    protected void setStatementForInsert(
            PreparedStatement preparedStatement, ToDo entity, Integer rowCount) throws SQLException {
        logger.info("Setting parameters for PreparedStatement to insert ToDo entity");
        preparedStatement.setString(rowCount * 4 + 1, entity.getTitle());
        preparedStatement.setString(rowCount * 4 + 2, entity.getDescription());
        preparedStatement.setDate(rowCount * 4 + 3, new java.sql.Date(entity.getDueDate().getTime()));
        preparedStatement.setInt(rowCount * 4 + 4, entity.getLevelOfImportance());
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
