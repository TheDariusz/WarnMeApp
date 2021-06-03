package com.thedariusz.warnme.twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Media {

    private String type;
    private String mediaKey;
    private String url;

    public Media() {
    }

    public Media(String type, String mediaKey, String url) {
        this.type = type;
        this.mediaKey = mediaKey;
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMediaKey() {
        return mediaKey;
    }

    @JsonProperty("media_key")
    public void setMediaKey(String mediaKey) {
        this.mediaKey = mediaKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
