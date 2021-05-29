package com.thedariusz.warnme.twitter.model;

public class Media {
    private String type;
    private int height;
    private int width;
    private String media_key;
    private String url;

    public Media(String type, int height, int width, String media_key, String url) {
        this.type = type;
        this.height = height;
        this.width = width;
        this.media_key = media_key;
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getMedia_key() {
        return media_key;
    }

    public void setMedia_key(String media_key) {
        this.media_key = media_key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Media() {
    }
}
