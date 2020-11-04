package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.EventService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class LogoutPage extends Page {

    private final EventService eventService = new EventService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        User user = (User)request.getSession().getAttribute("user");
        request.getSession().removeAttribute("user");;
        eventService.save(new Event(user.getId(), Event.Type.LOGOUT));
        request.getSession().setAttribute("message", "Good bye. Hope to see you soon!");
        throw new RedirectException("/index");
    }
}
