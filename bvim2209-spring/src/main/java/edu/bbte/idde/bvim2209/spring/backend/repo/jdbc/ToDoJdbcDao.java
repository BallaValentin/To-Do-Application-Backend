package edu.bbte.idde.bvim2209.spring.backend.repo.jdbc;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.model.User;
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
import java.util.*;

@Repository
@Profile("jdbc")
@Slf4j
public class ToDoJdbcDao extends JdbcDao<ToDo> implements ToDoDao {
    private final DataSource dataSource;

    @Autowired
    public ToDoJdbcDao(DataSource dataSource) {
        super(dataSource, "ToDoSpring", "ID", 1,
                new ArrayList<>(Arrays.asList("Title", "Description", "DueDate", "ImportanceLevel")
                ));
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
    public Collection<ToDo> findByLevelOfImportance(Integer levelOfImportance) {
        String query = "SELECT * FROM ToDoSpring WHERE ImportanceLevel = " + levelOfImportance;

        Collection<ToDo> entities = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setInt(1, levelOfImportance);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ToDo entity = mapResultSetToEntity(resultSet);
                entities.add(entity);
            }
        } catch (SQLException exception) {
            log.error("Error fetching all entities from database", exception);
        }
        log.info("All entities have been successfully fetched from database");
        return entities;
    }

    @Override
    public Optional<ToDo> findByIdAndUser(Long id, User user) {
        return Optional.empty();
    }
}
