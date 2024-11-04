package edu.bbte.idde.bvim2209.repo.jdbc;

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
    private static final Logger logger = LoggerFactory.getLogger(JdbcDao.class);

    private static final HikariDataSource dataSource = DataSourceFactory.getDataSource();

    protected JdbcDao() {
    }

    @Override
    public Collection<T> findAll() {
        logger.info("Trying to fetch all entities from database");
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
            logger.error("Error fetching all entities from database", exception);
        }
        logger.info("All entities have been successfully fetched from database");
        return entities;
    }

    protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    protected abstract void setStatementForInsert(PreparedStatement preparedStatement, T entity) throws SQLException;

    protected abstract void setStatementForUpdate(PreparedStatement preparedStatement, T entity) throws SQLException;

    @Override
    public void create(T entity) throws IllegalArgumentException {
        logger.info("Trying to insert new entity in database");
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
            logger.error("Error creating entity in database", exception);
            throw new IllegalArgumentException("Could not create entity", exception);
        }
        logger.info("New entity has been successfully inserted into database");
    }

    @Override
    public T findById(Long id) throws EntityNotFoundException {
        logger.info("Trying to find entity by id: " + id + " in database");
        String query = "SELECT * FROM ToDo WHERE ID=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    logger.info("Entity with id " + id + " has been successfully found in database");
                    return mapResultSetToEntity(resultSet);
                } else {
                    throw new EntityNotFoundException("Entity with ID " + id + " not found");
                }
            }
        } catch (SQLException exception) {
            logger.error("Error finding entity by ID in database", exception);
            throw new EntityNotFoundException("Entity with ID " + id + " not found.");
        }
    }

    @Override
    public void update(T entity) throws EntityNotFoundException {
        logger.info("Trying to update entity with id " + entity.getId() + " in database");
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
            logger.error("Error updating entity in database", exception);
        }
        logger.info("Entity with id " + entity.getId() + " has been successfully updated in database");
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        logger.info("Trying to delete entity with id " + id + " from database");
        String query = "DELETE FROM ToDo WHERE ID=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new EntityNotFoundException("Entity with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            logger.error("Error deleting entity from database", e);
        }
        logger.info("Entity with id " + id + " has been successfully deleted from database");
    }
}
