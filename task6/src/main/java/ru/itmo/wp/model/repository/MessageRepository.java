package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Message;
import ru.itmo.wp.model.domain.User;

import java.util.List;

public interface MessageRepository {
    void save(Message message);
    List<Message> findByUserId(long id);
    Message find(long id);
}
