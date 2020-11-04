package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.repository.EventRepository;


import java.sql.*;

public class EventRepositoryImpl extends BasicRepositoryImpl<Event> implements EventRepository {

    public void save(Event event) {
        super.save(event, String.format("INSERT INTO `Event` (`userId`, `creationTime`, `type`) VALUES (%1$d, NOW(), \"%2$s\")",
                event.getUserId(), event.getType().toString()));
    }

    public Event find(long id) {
        return super.find("SELECT * FROM `Event` WHERE `id`=" + id);
    }

    Event toItem(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Event event = new Event();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    event.setId(resultSet.getLong(i));
                    break;
                case "type":
                    event.setType(Event.Type.valueOf(resultSet.getString(i)));
                    break;
                case "creationTime":
                    event.setCreationTime(resultSet.getTimestamp(i));
                    break;
                case "userId":
                    event.setUserId(resultSet.getLong(i));
                default:
                    // No operations.
            }
        }
        return event;
    }

    @Override
    String getFindErrorString() {
        return "Can't find Error";
    }

    @Override
    String getSaveErrorString() {
        return "Can't save Error";
    }

    @Override
    void setId(Event item, long id) {
        item.setId(id);
    }

    @Override
    void setCreationTime(Event item) {
        item.setCreationTime(find(item.getId()).getCreationTime());
    }
}
