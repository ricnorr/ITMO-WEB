package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @noinspection unused
 */
public class UsersPage {
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    private void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("user",
                userService.find(Long.parseLong(request.getParameter("userId"))));
    }


    private void setAdmin(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User user = (User) request.getSession().getAttribute("user");
        userService.isAdmin(user);
        userService.validateUserId(request.getParameter("id"));
        user = userService.find(Long.parseLong(request.getParameter("id")));
        userService.setAdmin(user);
        view.put("message", "admin");
    }

    private void setUser(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User user = (User) request.getSession().getAttribute("user");
        userService.isAdmin(user);
        userService.validateUserId(request.getParameter("id"));
        User targetUser = userService.find(Long.parseLong(request.getParameter("id")));
        userService.setUser(targetUser);
        userService.copySameUsers(user, targetUser);
        view.put("message", "user");
    }


}
