package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;

import java.util.List;

public interface ArticleRepository {
    Article find(long id);
    void save(Article article);
    List<Article> getAllArticles();
    List<Article> getAllNotHidden();
    void setHidden(long id);
    void setShow(long id);
    List<Article> getByUserId(long id);
}
