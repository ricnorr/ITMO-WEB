package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.MessageService;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

abstract public class Page {
    private HttpServletRequest request;
    protected static final UserService userService = new UserService();
    protected static final MessageService messageService = new MessageService();

    public void before(HttpServletRequest request, Map<String, Object> view) {
        this.request = request;
        view.put("userCount", new UserService().countUsers());
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            view.put("user", user);
        }

        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }

    public void setMessage(HttpServletRequest request, String message) {
        request.getSession().setAttribute("message", message);
    }

    public void setUser(User user) {
        request.getSession().setAttribute("user", user);
    }

    public User getUser() {
        return (User)request.getSession().getAttribute("user");
    }

    public void after(Map<String, Object> view) {

    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        //Nothing
    }
}

