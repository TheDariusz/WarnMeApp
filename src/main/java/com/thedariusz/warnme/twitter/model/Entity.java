package com.thedariusz.warnme.twitter.model;

import java.util.List;

public class Entity {

    List<Url> urls;
    List<Hashtag> hashtags;

    public Entity() {
    }

    public Entity(List<Url> urls, List<Hashtag> hashtags) {
        this.urls = urls;
        this.hashtags = hashtags;
    }

    public List<Url> getUrls() {
        return urls == null ? List.of() : urls;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }

    public List<Hashtag> getHashtags() {
        return hashtags == null ? List.of() : hashtags;
    }

    public void setHashtags(List<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }
}
