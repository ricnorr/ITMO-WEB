package ru.itmo.wp.model.domain;

import java.util.Date;

public class Event {

    public Event() {

    }

    public enum Type {
        ENTER, LOGOUT
    }

    private long id;
    private long userId;
    private Type type;
    private Date creationTime;

    public Event(long userId, Type type) {
        this.userId = userId;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public Type getType() {
        return type;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
