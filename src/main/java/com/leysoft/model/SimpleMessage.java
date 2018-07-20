
package com.leysoft.model;

public class SimpleMessage {

    private String title;

    private String description;

    private Author from;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Author getFrom() {
        return from;
    }

    public void setFrom(Author from) {
        this.from = from;
    }

}
