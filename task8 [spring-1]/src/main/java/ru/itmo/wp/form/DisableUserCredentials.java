package ru.itmo.wp.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class DisableUserCredentials {
    @PositiveOrZero
    long id;

    @NotNull
    String action;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
