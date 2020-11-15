package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Message;
import ru.itmo.wp.model.repository.MessageRepository;

import java.sql.*;
import java.util.List;

public class MessageRepositoryImpl extends BasicRepositoryImpl<Message> implements MessageRepository {

    public Message toItem(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }
        Message message = new Message();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    message.setId(resultSet.getLong(i));
                    break;
                case "sourceUserId":
                    message.setSourceUserId(resultSet.getLong(i));
                    break;
                case "creationTime":
                    message.setCreationTime(resultSet.getTimestamp(i));
                    break;
                case "targetUserId":
                    message.setTargetUserId(resultSet.getLong(i));
                case "text":
                    message.setText(resultSet.getString(i));
                default:
                    // No operations.
            }
        }
        return message;
    }

    @Override
    String getFindErrorString() {
        return "Can't find message";
    }

    @Override
    String getSaveErrorString() {
        return "Can't save message";
    }

    @Override
    void setId(Message item, long id) {
        item.setId(id);
    }

    @Override
    void setCreationTime(Message item) {
        item.setCreationTime(find(item.getId()).getCreationTime());
    }

    public Message find(long id) {
        return super.find("SELECT * FROM `Message` WHERE `id`=" + id);
    }

    public List<Message> findByUserId(long userId) {
        return super.findList(String.format("SELECT * FROM `Message` WHERE `sourceUserId`=\"%1$S\" OR targetUserId=\"%1$S\" ORDER BY creationTime", userId));
    }

    public void save(Message message) {
        super.save(message, String.format("INSERT INTO `Message` (`sourceUserId`, `targetUserId`, `creationTime`, `text`) VALUES (\"%1$s\", \"%2$s\",NOW(),\"%3$s\")",
                message.getSourceUserId(), message.getTargetUserId(), message.getText()));

    }
}
