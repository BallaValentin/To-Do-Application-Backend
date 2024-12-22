package edu.bbte.idde.bvim2209.spring.backend.repo.jdbc;

import edu.bbte.idde.bvim2209.spring.backend.model.BaseEntity;
import edu.bbte.idde.bvim2209.spring.backend.repo.Dao;
import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public abstract class JdbcDao<T extends BaseEntity> implements Dao<T> {
    protected JdbcDao() {
    }

    protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    protected abstract void setStatementForInsert(PreparedStatement preparedStatement, T entity) throws SQLException;

    protected abstract void setStatementForInsertWithId(
            PreparedStatement preparedStatement, T entity) throws SQLException;

    protected abstract void setStatementForUpdate(PreparedStatement preparedStatement, T entity) throws SQLException;

    protected abstract PreparedStatement prepareStatementForFindAll() throws SQLException;

    protected abstract PreparedStatement prepareStatementForInsert() throws SQLException;

    protected abstract PreparedStatement prepareStatementForInsertWithId() throws SQLException;

    protected abstract PreparedStatement prepareStatementForFindById(Long id) throws SQLException;

    protected abstract PreparedStatement prepareStatementForDeleteById(Long id) throws SQLException;

    protected abstract PreparedStatement prepareStatementForUpdate() throws SQLException;

    protected abstract Integer getPrimaryKeyIndex();

    protected abstract Integer getNumberOfColumnsToUpdate();

    @Override
    public Collection<T> findAll() {
        log.info("Trying to fetch all entities from database");
        Collection<T> entities = new ArrayList<>();
        try (
                PreparedStatement preparedStatement = prepareStatementForFindAll();
                ResultSet resultSet = preparedStatement.executeQuery()
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

        if (entity.getId() != null && !findAll().contains(entity)) {
            log.debug("Inserting new entity with id in database");
            return createWithId(entity);
        } else {
            return createWithoutId(entity);
        }
    }

    @Override
    public T getById(Long id) throws EntityNotFoundException {
        log.info("Trying to find entity by id: {} in database", id);
        try (
                PreparedStatement preparedStatement = prepareStatementForFindById(id)) {
            preparedStatement.setLong(getPrimaryKeyIndex(), id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    log.info("Entity with id {} has been successfully found in database", id);
                    return mapResultSetToEntity(resultSet);
                } else {
                    throw new EntityNotFoundException("Entity with ID " + id + " not found");
                }
            }
        } catch (SQLException exception) {
            log.error("Error finding entity by ID in database", exception);
            throw new EntityNotFoundException("Entity with ID " + id + " not found.");
        }
    }

    @Override
    public void update(T entity) throws EntityNotFoundException {
        log.info("Trying to update entity with id {} in database", entity.getId());
        try (
                PreparedStatement preparedStatement = prepareStatementForUpdate()) {
            setStatementForUpdate(preparedStatement, entity);
            preparedStatement.setLong(getNumberOfColumnsToUpdate() + 1, entity.getId());
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
        try (
                PreparedStatement preparedStatement = prepareStatementForDeleteById(id)) {
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new EntityNotFoundException("Entity with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            log.error("Error deleting entity from database", e);
        }
        log.info("Entity with id {} has been successfully deleted from database", id);
    }

    private T createWithoutId(T entity) {
        try (
                PreparedStatement preparedStatement = prepareStatementForInsert()
        ) {
            setStatementForInsert(preparedStatement, entity);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getLong(getPrimaryKeyIndex()));
                    }
                }
            }
            logEntityCreated();
            return entity;
        } catch (SQLException exception) {
            log.error("Error creating entity in database", exception);
            throw new IllegalArgumentException("Could not create entity", exception);
        }
    }

    private T createWithId(T entity) {
        try (
                PreparedStatement preparedStatement = prepareStatementForInsertWithId()
        ) {
            setStatementForInsertWithId(preparedStatement, entity);
            preparedStatement.executeUpdate();
            logEntityCreated();
            return entity;
        } catch (SQLException exception) {
            log.error("Error creating entity in database", exception);
            throw new IllegalArgumentException("Could not create entity", exception);
        }
    }

    private void logEntityCreated() {
        log.info("New entity has been successfully inserted into database");
    }
}
