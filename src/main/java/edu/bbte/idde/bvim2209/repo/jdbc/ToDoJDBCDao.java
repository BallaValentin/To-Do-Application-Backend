package edu.bbte.idde.bvim2209.repo.jdbc;

import edu.bbte.idde.bvim2209.model.ToDo;
import edu.bbte.idde.bvim2209.repo.ToDoDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class ToDoJDBCDao extends JDBCDao<ToDo> implements ToDoDao {
    @Override
    public Collection<ToDo> findByTitle(String title) {
        return null;
    }

    @Override
    protected ToDo mapResultSetToEntity(ResultSet resultSet) throws SQLException {
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
        preparedStatement.setString(2, entity.getTitle());
        preparedStatement.setString(3, entity.getDescription());
        preparedStatement.setDate(4, new java.sql.Date(entity.getDueDate().getTime()));
        preparedStatement.setInt(5, entity.getLevelOfImportance());
    }
}
