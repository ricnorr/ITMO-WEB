package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.exception.RepositoryException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BasicRepositoryImpl<T> {

    //0:30
    protected final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    abstract T toItem(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException;
    abstract String getFindErrorString();
    abstract String getSaveErrorString();
    abstract void setId(T item, long id);
    abstract void setCreationTime(T item);


    public T find(String query) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    return toItem(statement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(getFindErrorString(), e);
        }
    }

    public List<T> findList(String query) {
        List<T> items = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    T item = null;
                    while ((item = toItem(resultSet.getMetaData(), resultSet)) != null) {
                        items.add(item);
                    }
                }
            } catch (SQLException e) {
                throw new RepositoryException(getFindErrorString(), e);
            }
        } catch (SQLException e) {
            throw new RepositoryException(getFindErrorString(), e);
        }
        return items;
    }

    public void save(T item, String query) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException(getSaveErrorString());
                } else {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        setId(item, generatedKeys.getLong(1));
                        setCreationTime(item);
                    } else {
                        throw new RepositoryException(getSaveErrorString());
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(getSaveErrorString(), e);
        }
    }
}
