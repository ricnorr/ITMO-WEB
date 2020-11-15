package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class ArticlePage {
    private final ArticleService articleService = new ArticleService();
    private final UserService userService = new UserService();
    private void action(HttpServletRequest request, Map<String, Object> view) {
        //
    }

    private void send(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        userService.validateUserNotNull(user);
        Article article = new Article(user.getId(), request.getParameter("title"), request.getParameter("text"));
        articleService.validateArticle(article);
        articleService.save(article);
        request.getSession().setAttribute("message", "Article was sent");
    }
}

