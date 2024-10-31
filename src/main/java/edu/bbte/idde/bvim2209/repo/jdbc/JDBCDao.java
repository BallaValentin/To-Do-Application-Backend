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

public abstract class JDBCDao<T extends BaseEntity> implements Dao<T> {
    private static final HikariDataSource dataSource = new HikariDataSource();
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public JDBCDao() {
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
                ResultSet resultSet = preparedStatement.executeQuery();
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

    @Override
    public void create(T entity) throws IllegalArgumentException {

    }

    @Override
    public T findById(Long id) throws EntityNotFoundException {
        return null;
    }

    @Override
    public void update(T entity) throws EntityNotFoundException {

    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {

    }
}
