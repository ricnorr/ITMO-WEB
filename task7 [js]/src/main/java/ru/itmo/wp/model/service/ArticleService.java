package ru.itmo.wp.model.service;


import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;


import java.util.List;

public class ArticleService {
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();
    private final UserService userService = new UserService();

    public void save(Article article) {
        articleRepository.save(article);
    }

    public void validateArticle(Article article) throws ValidationException {
        if (article == null) {
            throw new ValidationException("Article can't be empty");
        }
        if (Strings.isNullOrEmpty(article.getText()) || article.getText().length() > 255) {
            throw new ValidationException("Article's text should have 1..255 symbols");
        }
        if (Strings.isNullOrEmpty(article.getTitle()) || article.getTitle().length() > 255) {
            throw new ValidationException("Article's title should have 1..255 symbols");
        }
    }

    public List<Article> getAll() {
        return articleRepository.getAllArticles();
    }

    public List<Article> getAllNotHidden() {
        return articleRepository.getAllNotHidden();
    }

    public void setShow(long id) throws ValidationException {
        Article article =articleRepository.find(id);
        if (article == null) {
            throw new ValidationException("Article not found");
        }
        articleRepository.setShow(id);
    }

    public void setHidden(long id) throws ValidationException {
        Article article =articleRepository.find(id);
        if (article == null) {
            throw new ValidationException("Article not found");
        }
        articleRepository.setHidden(id);
    }



    public void validateShowHideRequest(String id, User user) throws ValidationException {
        userService.validateUserNotNull(user);
        if (id == null) {
            throw new ValidationException("Article's id, requested to be hidden, is not given");
        }
        Article article = articleRepository.find(Long.parseLong(id));
        if (article == null) {
            throw new ValidationException("Article doesn't exist");
        }
        if (article.getUserId() != user.getId()) {
            throw new ValidationException("Not-author can't hide/show article");
        }
    }

    public List<Article> getByUserId(long userId) {
        return articleRepository.getByUserId(userId);
    }


}
