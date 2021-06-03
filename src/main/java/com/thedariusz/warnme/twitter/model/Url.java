package com.thedariusz.warnme.twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Url {

    private String expandedUrl;
    private int status;
    private String title;
    private String description;

    public Url() {
    }

    public Url(String expandedUrl, int status, String title, String description) {
        this.expandedUrl = expandedUrl;
        this.status = status;
        this.title = title;
        this.description = description;
    }

    public String getExpandedUrl() {
        return expandedUrl;
    }

    @JsonProperty("expanded_url")
    public void setExpandedUrl(String expandedUrl) {
        this.expandedUrl = expandedUrl;
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
}
