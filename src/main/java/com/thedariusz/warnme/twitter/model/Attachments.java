package com.thedariusz.warnme.twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Attachments {

    private List<String> mediaKeys;

    public Attachments() {
    }

    public Attachments(List<String> mediaKeys) {
        this.mediaKeys = mediaKeys;
    }

    public List<String> getMediaKeys() {
        return mediaKeys;
    }

    @JsonProperty("media_keys")
    public void setMediaKeys(List<String> mediaKeys) {
        this.mediaKeys = mediaKeys;
    }
}
