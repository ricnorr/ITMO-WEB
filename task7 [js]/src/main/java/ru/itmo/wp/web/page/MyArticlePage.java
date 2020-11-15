package ru.itmo.wp.web.page;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.annotation.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyArticlePage {
    private final ArticleService articleService = new ArticleService();

    public void action(HttpServletRequest request, Map<String, Object> view) throws  ValidationException {
        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            throw new ValidationException("You should be logged in!");
        }
        view.put("articles", articleService.getByUserId(user.getId()));
    }

    @Json
    public void hide(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        articleService.validateShowHideRequest(request.getParameter("id"), (User)request.getSession().getAttribute("user"));
        articleService.setHidden(Long.parseLong(request.getParameter("id")));
        view.put("message", "show");
    }

    @Json
    public void show(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        articleService.validateShowHideRequest(request.getParameter("id"), (User)request.getSession().getAttribute("user"));
        articleService.setShow(Long.parseLong(request.getParameter("id")));
        view.put("message", "hide");
    }
}
