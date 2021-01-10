package ru.itmo.wp.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CommentForm {
    @NotNull
    @Size(min = 1, max = 255)
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
