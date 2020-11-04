package ru.itmo.wp.model.domain;

import ru.itmo.wp.model.repository.impl.MessageRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.util.Date;

public class Message {
    private long id;
    private long sourceUserId;
    private long targetUserId;
    private String text;
    private Date creationTime;

    public Message(long sourceUserId, long targetUserId, String text) {
        this.sourceUserId = sourceUserId;
        this.targetUserId = targetUserId;
        this.text = text;
    }

    public Message() {
    }

    public long getSourceUserId() {
        return sourceUserId;
    }

    public long getTargetUserId() {
        return targetUserId;
    }

    public String getText() {
        return text;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setSourceUserId(long sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    public void setTargetUserId(long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public void setText(String text) {
        this.text = text;
    }

}
