package ru.itmo.web.lesson4.model;

public class Post {
    private long id;
    private String text;
    private String title;
    private long user_id;

    public Post(long id, long user_id, String title, String text) {
        this.id = id;
        this.text = text;
        this.title = title;
        this.user_id = user_id;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public long getUser_id() {
        return user_id;
    }


}
