package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl extends BasicRepositoryImpl<User> implements UserRepository {

    @Override
    public User find(long id) {
        return super.find(String.format("SELECT * FROM `User` WHERE `id`=%1$d", id));
    }

    @Override
    public User findByLogin(String login) {
        return super.find(String.format("SELECT * FROM `User` WHERE `login`=\"%1$s\"", login));
    }

    @Override
    public User findByEmail(String email) {
        return super.find(String.format("SELECT * FROM `User` WHERE `email`=\"%1$s\"", email));
    }

    @Override
    public User findByLoginAndPasswordSha(String login, String passwordSha) {
        return super.find(String.format("SELECT * FROM `User` WHERE `login`=\"%1$s\" AND `passwordSha`=\"%2$s\"", login, passwordSha));
    }

    @Override
    public User findByLoginOrEmailAndPasswordSha(String loginOrEMail, String passwordSha) {
        return super.find(String.format("SELECT * FROM `User` WHERE (`login`=\"%1$s\" OR `email`=\"%1$s\") AND `passwordSha`=\"%2$s\"", loginOrEMail, passwordSha));
    }

    @Override
    public List<User> findAll() {
        return super.findList("SELECT * FROM `User` ORDER BY `id` DESC");
    }

    public User toItem(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        User user = new User();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    user.setId(resultSet.getLong(i));
                    break;
                case "login":
                    user.setLogin(resultSet.getString(i));
                    break;
                case "creationTime":
                    user.setCreationTime(resultSet.getTimestamp(i));
                    break;
                case "email":
                    user.setEmail(resultSet.getString(i));
                default:
                    // No operations.
            }
        }
        return user;
    }

    @Override
    String getFindErrorString() {
        return "Can't find User";
    }

    @Override
    String getSaveErrorString() {
        return "Can't save User";
    }

    @Override
    void setId(User item, long id) {
         item.setId(id);
    }

    @Override
    void setCreationTime(User item) {
         item.setCreationTime(find(item.getId()).getCreationTime());
    }

    @Override
    public void save(User user, String passwordSha) {
        super.save(user, String.format("INSERT INTO `User` (`login`, `passwordSha`, `creationTime`, `email`) VALUES (\"%1$s\", \"%2$s\", NOW(), \"%3$s\")", user.getLogin(), passwordSha, user.getEmail()));
    }

    @Override
    public long findCount() {
        try (Connection connection = super.DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM `User`", Statement.RETURN_GENERATED_KEYS)) {
                try {
                    ResultSet generatedKeys = statement.executeQuery();
                    if (generatedKeys.next()) {
                        return generatedKeys.getLong(1);
                    } else {
                        throw new RepositoryException("Can't get number of registered users");
                    }
                } catch (SQLException e) {
                    throw new RepositoryException("Can't get number of registered users");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't count users", e);
        }
    }
}
