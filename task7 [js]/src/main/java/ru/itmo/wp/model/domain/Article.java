package ru.itmo.wp.model.domain;

import java.util.Date;

public class Article {
    private long id;
    private long userId;
    private String title;
    private String text;
    private Date creationTime;
    private boolean hidden;

    public Article() {
    }

    public Article(long userId, String title, String text) {
        this.userId = userId;
        this.title = title;
        this.text = text;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
