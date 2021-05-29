package com.thedariusz.warnme.twitter.model;

public class Entity {
    Url[] urls;
    Hashtag[] hashtags;

    public Entity(Url[] urls, Hashtag[] hashtags) {
        this.urls = urls;
        this.hashtags = hashtags;
    }

    public Url[] getUrls() {
        return urls;
    }

    public void setUrls(Url[] urls) {
        this.urls = urls;
    }

    public Hashtag[] getHashtags() {
        return hashtags;
    }

    public void setHashtags(Hashtag[] hashtags) {
        this.hashtags = hashtags;
    }

    public Entity() {
    }
}
