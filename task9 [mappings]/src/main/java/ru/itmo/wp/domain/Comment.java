package ru.itmo.wp.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table
public class Comment {

    @Id
    @GeneratedValue
    private long id;

    @Lob
    @NotNull
    @NotEmpty
    String text;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name="post_id")
    Post post;

    @CreationTimestamp
    private Date creationTime;

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreationTime() {
        return creationTime;
    }

}
