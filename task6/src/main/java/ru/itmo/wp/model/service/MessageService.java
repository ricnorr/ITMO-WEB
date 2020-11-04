package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Message;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.MessageRepository;
import ru.itmo.wp.model.repository.impl.MessageRepositoryImpl;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class MessageService {
    private final MessageRepository messageRepository = new MessageRepositoryImpl();

    public List<Message> findByUserId(long id) {
        return messageRepository.findByUserId(id);
    }

    public void save(Message message) {
        messageRepository.save(message);
    }


    public void validate(HttpServletRequest request) throws ValidationException {
        String message = request.getParameter("textMessage");
        if (message == null || message.length() == 0 || message.length() > 255) {
            throw new ValidationException("Messages size can't be more than 255 characters or empty");
        }
        new UserService().validate(request.getParameter("targetUserId"));
    }
}
