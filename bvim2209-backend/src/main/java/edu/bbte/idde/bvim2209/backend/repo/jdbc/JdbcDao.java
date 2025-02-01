package edu.bbte.idde.bvim2209.backend.repo.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.bvim2209.backend.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.backend.model.BaseEntity;
import edu.bbte.idde.bvim2209.backend.repo.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class JdbcDao<T extends BaseEntity> implements Dao<T> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcDao.class);

    static final HikariDataSource dataSource = DataSourceFactory.getDataSource();

    private final String tableName;
    private final String primaryKey;
    private final Integer primaryKeyIndex;
    private final Collection<String> columnNames;

    protected JdbcDao(String tableName, String primaryKey, Integer primaryKeyIndex,
                      Collection<String> columnNames) {
        this.tableName = tableName;
        this.primaryKey = primaryKey;
        this.primaryKeyIndex = primaryKeyIndex;
        this.columnNames = columnNames;
    }

    protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    protected abstract void setStatementForInsert(
            PreparedStatement preparedStatement, T entity, Integer rowCount) throws SQLException;

    protected abstract void setStatementForUpdate(PreparedStatement preparedStatement, T entity) throws SQLException;

    @Override
    public Collection<T> findAll() {
        logger.info("Trying to fetch all entities from database");

        String query = "SELECT * FROM " + tableName;
        Collection<T> entities = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery(query)
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

    @Override
    public void create(T entity) throws IllegalArgumentException {
        logger.info("Trying to insert new entity in database");

        String query = "INSERT INTO ToDo ("
                + String.join(",", columnNames) + ") VALUES ("
                + columnNames.stream().map(column -> "?").collect(Collectors.joining(", "))
                + ")";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            setStatementForInsert(preparedStatement, entity, 0);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getLong(primaryKeyIndex));
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
    public void addEntities(List<T> entities) throws IllegalArgumentException {
        logger.info("Trying to insert new entities in database");

        String query = "INSERT INTO ToDo ("
                + String.join(", ", columnNames) + ") VALUES "
                + entities.stream()
                .map(entity -> "(" + columnNames.stream().map(column -> "?").collect(Collectors.joining(", ")) + ")")
                .collect(Collectors.joining(", "));

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement =
                        connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            for (int i = 0; i < entities.size(); i++) {
                setStatementForInsert(preparedStatement, entities.get(i), i);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            logger.error("Error creating entities in database", exception);
            throw new IllegalArgumentException("Could not create entities", exception);
        }
        logger.info("New entities has been successfully inserted into database");
    }

    @Override
    public T findById(Long id) throws EntityNotFoundException {
        logger.info("Trying to find entity by id: {} in database", id);

        String query = "SELECT * FROM " + tableName + " WHERE " + primaryKey + " = " + id;
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    logger.info("Entity with id {} has been successfully found in database", id);
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
        logger.info("Trying to update entity with id {} in database", entity.getId());

        String query = "UPDATE " + tableName
                + " SET "
                + columnNames.stream().map(column -> column + "=?").collect(Collectors.joining(", "))
                + " WHERE " + primaryKey + "=?";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setStatementForUpdate(preparedStatement, entity);
            preparedStatement.setLong(columnNames.size() + 1, entity.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new EntityNotFoundException("Entity with ID " + entity.getId() + " not found.");
            }
        } catch (SQLException exception) {
            logger.error("Error updating entity in database", exception);
        }
        logger.info("Entity with id {} has been successfully updated in database", entity.getId());
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        logger.info("Trying to delete entity with id {} from database", id);

        String query = "DELETE FROM ToDo WHERE " + primaryKey + " = " + id;

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new EntityNotFoundException("Entity with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            logger.error("Error deleting entity from database", e);
        }
        logger.info("Entity with id {} has been successfully deleted from database", id);
    }
}
