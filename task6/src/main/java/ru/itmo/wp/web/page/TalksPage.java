package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Message;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TalksPage extends Page {
    public static void action(HttpServletRequest request, Map<String, Object> view) {
         User user = (User) request.getSession().getAttribute("user");
         if (user != null) {
             List<User> userList = userService.findAll();
             view.put("users", userList);
             List<Message> messages = messageService.findByUserId(user.getId());
             List<User> usersSource = new ArrayList<>();
             List<User> usersTarget = new ArrayList<>();
             for (Message message : messages) {
                 usersSource.add(userService.find(message.getSourceUserId()));
                 usersTarget.add(userService.find(message.getTargetUserId()));
             }
             view.put("messages", messages);
             view.put("usersSource", usersSource);
             view.put("usersTarget", usersTarget);
             view.put("size", messages.size());
         } else {
             request.getSession().setAttribute("message", "You should be logged-in to read messages!");
             throw new RedirectException("/index");
         }
   }

   public static void sendMessage(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        action(request, view);
        messageService.validate(request);
        messageService.save(new Message(user.getId(), userService.find(Long.parseLong(request.getParameter("targetUserId"))).getId(), request.getParameter("textMessage")));
        throw new RedirectException("/talks");
   }
}
