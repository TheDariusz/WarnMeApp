package com.thedariusz.warnme.twitter.model;

public class Url {
    private String expanded_url;
    private int status;
    private String title;
    private String description;

    public Url(String expanded_url, int status, String title, String description) {
        this.expanded_url = expanded_url;
        this.status = status;
        this.title = title;
        this.description = description;
    }

    public String getExpanded_url() {
        return expanded_url;
    }

    public void setExpanded_url(String expanded_url) {
        this.expanded_url = expanded_url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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

    public Url() {
    }
}
