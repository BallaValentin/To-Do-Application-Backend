package edu.bbte.idde.bvim2209.spring.backend.repo.jdbc;

import edu.bbte.idde.bvim2209.spring.backend.model.BaseEntity;
import edu.bbte.idde.bvim2209.spring.backend.repo.Dao;
import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public abstract class JdbcDao<T extends BaseEntity> implements Dao<T> {

    @Autowired
    private final DataSource dataSource;
    private final String tableName;
    private final String primaryKey;
    private final Integer primaryKeyIndex;
    private final Collection<String> columnNames;

    protected JdbcDao(DataSource dataSource,
                      String tableName,
                      String primaryKey,
                      Integer primaryKeyIndex,
                      Collection<String> columnNames) {
        this.dataSource = dataSource;
        this.tableName = tableName;
        this.primaryKey = primaryKey;
        this.primaryKeyIndex = primaryKeyIndex;
        this.columnNames = columnNames;
    }

    protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    protected abstract void setStatementForInsert(PreparedStatement preparedStatement, T entity) throws SQLException;

    protected abstract void setStatementForUpdate(PreparedStatement preparedStatement, T entity) throws SQLException;

    @Override
    public Collection<T> findAll(Specification<T> specification) {
        log.info("Trying to fetch all entities from database");

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
            log.error("Error fetching all entities from database", exception);
        }
        log.info("All entities have been successfully fetched from database");
        return entities;
    }

    @Override
    public T saveAndFlush(T entity) throws IllegalArgumentException {
        log.info("Trying to insert new entity in database");

        String query = "INSERT INTO " + tableName + " ("
                + String.join(",", columnNames) + ") VALUES ("
                + columnNames.stream().map(column -> "?").collect(Collectors.joining(", "))
                + ")";
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
                        entity.setId(generatedKeys.getLong(primaryKeyIndex));
                    }
                }
            }
        } catch (SQLException exception) {
            log.error("Error creating entity in database", exception);
            throw new IllegalArgumentException("Could not create entity", exception);
        }
        log.info("New entity has been successfully inserted into database");
        return entity;
    }

    @Override
    public Optional<T> findById(Long id) throws EntityNotFoundException {
        log.info("Trying to find entity by id: {} in database", id);

        String query = "SELECT * FROM " + tableName + " WHERE " + primaryKey + " = " + id;
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    log.info("Entity with id {} has been successfully found in database", id);
                    T entity = mapResultSetToEntity(resultSet);
                    return Optional.of(entity);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException exception) {
            log.error("Error finding entity by ID in database", exception);
            return Optional.empty();
        }
    }

    @Override
    public void update(T entity) throws EntityNotFoundException {
        log.info("Trying to update entity with id {} in database", entity.getId());

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
            log.error("Error updating entity in database", exception);
        }
        log.info("Entity with id {} has been successfully updated in database", entity.getId());
    }

    @Override
    public void deleteById(Long id) throws EntityNotFoundException {
        log.info("Trying to delete entity with id {} from database", id);

        String query = "DELETE FROM " + tableName + " WHERE " + primaryKey + " = " + id;

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new EntityNotFoundException("Entity with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            log.error("Error deleting entity from database", e);
        }
        log.info("Entity with id {} has been successfully deleted from database", id);
    }
}
