package edu.bbte.idde.bvim2209.repo.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.bvim2209.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.model.BaseEntity;
import edu.bbte.idde.bvim2209.repo.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public abstract class JdbcDao<T extends BaseEntity> implements Dao<T> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcDao.class);

    static final HikariDataSource dataSource = DataSourceFactory.getDataSource();

    protected JdbcDao() {
    }

    protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    protected abstract void setStatementForInsert(PreparedStatement preparedStatement, T entity) throws SQLException;

    protected abstract void setStatementForUpdate(PreparedStatement preparedStatement, T entity) throws SQLException;

    protected abstract PreparedStatement prepareStatementForFindAll() throws SQLException;

    protected abstract PreparedStatement prepareStatementForInsert() throws SQLException;

    protected abstract PreparedStatement prepareStatementForFindById(Long id) throws SQLException;

    protected abstract PreparedStatement prepareStatementForDeleteById(Long id) throws SQLException;

    protected abstract PreparedStatement prepareStatementForUpdate() throws SQLException;

    protected abstract Integer getPrimaryKeyIndex();

    protected abstract Integer getNumberOfColumnsToUpdate();

    @Override
    public Collection<T> findAll() {
        logger.info("Trying to fetch all entities from database");
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
            logger.error("Error fetching all entities from database", exception);
        }
        logger.info("All entities have been successfully fetched from database");
        return entities;
    }

    @Override
    public void create(T entity) throws IllegalArgumentException {
        logger.info("Trying to insert new entity in database");
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
        } catch (SQLException exception) {
            logger.error("Error creating entity in database", exception);
            throw new IllegalArgumentException("Could not create entity", exception);
        }
        logger.info("New entity has been successfully inserted into database");
    }

    @Override
    public T findById(Long id) throws EntityNotFoundException {
        logger.info("Trying to find entity by id: {} in database", id);
        try (
                PreparedStatement preparedStatement = prepareStatementForFindById(id)) {
            preparedStatement.setLong(getPrimaryKeyIndex(), id);
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
        try (
                PreparedStatement preparedStatement = prepareStatementForUpdate()) {
            setStatementForUpdate(preparedStatement, entity);
            preparedStatement.setLong(getNumberOfColumnsToUpdate() + 1, entity.getId());
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
        try (
                PreparedStatement preparedStatement = prepareStatementForDeleteById(id)) {
            preparedStatement.setLong(1, id);
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
