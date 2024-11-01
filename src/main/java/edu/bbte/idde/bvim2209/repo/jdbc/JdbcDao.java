package edu.bbte.idde.bvim2209.repo.jdbc;

import com.sun.tools.javac.Main;
import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.bvim2209.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.model.BaseEntity;
import edu.bbte.idde.bvim2209.repo.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public abstract class JdbcDao<T extends BaseEntity> implements Dao<T> {
    private static final HikariDataSource dataSource = new HikariDataSource();
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public JdbcDao() {
        logger.info("Setting up database connection parameters...");
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/ToDoDatabase");
        dataSource.setUsername("root");
        dataSource.setPassword("12345");
        dataSource.setMaximumPoolSize(10);
    }

    @Override
    public Collection<T> findAll() {
        Collection<T> entities = new ArrayList<>();
        String query = "SELECT * FROM ToDo";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                T entity = mapResultSetToEntity(resultSet);
                entities.add(entity);
            }
        } catch (SQLException exception) {
            logger.error("Error fetching all entities", exception);
        }
        return entities;
    }

    protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    protected abstract void setStatementForInsert(PreparedStatement preparedStatement, T entity) throws SQLException;

    protected abstract void setStatementForUpdate(PreparedStatement preparedStatement, T entity) throws SQLException;

    @Override
    public void create(T entity) throws IllegalArgumentException {
        String query = "INSERT INTO ToDo (Title, Description, DueDate, ImportanceLevel) VALUES (?, ?, ?, ?)";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            setStatementForInsert(preparedStatement, entity);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getLong(1));
                    }
                }
            }
        } catch (SQLException exception) {
            logger.error("Error creating entity", exception);
            throw new IllegalArgumentException("Could not create entity", exception);
        }
    }

    @Override
    public T findById(Long id) throws EntityNotFoundException {
        String query = "SELECT * FROM ToDo WHERE ID=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToEntity(resultSet);
                } else {
                    throw new EntityNotFoundException("Entity with ID " + id + " not found.");
                }
            }
        } catch (SQLException exception) {
            logger.error("Error finding entity by ID", exception);
            throw new EntityNotFoundException("Entity with ID " + id + " not found.");
        }
    }

    @Override
    public void update(T entity) throws EntityNotFoundException {
        String query = "UPDATE ToDo SET Title=?, Description=?, DueDate=?, ImportanceLevel=? WHERE ID=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setStatementForUpdate(preparedStatement, entity);
            preparedStatement.setLong(5, entity.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new EntityNotFoundException("Entity with ID " + entity.getId() + " not found.");
            }
        } catch (SQLException exception) {
            logger.error("Error updating entity", exception);
        }
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        String query = "DELETE FROM ToDo WHERE ID=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new EntityNotFoundException("Entity with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            logger.error("Error deleting entity", e);
        }
    }
}
