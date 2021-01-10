package ru.itmo.wp.form;

public class WritePostForm {

    private String text;
    private  String title;
    private  String tags;

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public String getTags() {
        return tags;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
