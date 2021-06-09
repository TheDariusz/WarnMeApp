package com.thedariusz.warnme.twitter.model;

import java.util.List;

public class TweetDtoWrapper {

    List<TweetDto> data;
    Includes includes;
    Meta meta;

    public TweetDtoWrapper(List<TweetDto> data, Includes includes, Meta meta) {
        this.data = data;
        this.includes = includes;
        this.meta = meta;
    }

    public TweetDtoWrapper() {
    }

    public List<TweetDto> getData() {
        return data;
    }

    public void setData(List<TweetDto> data) {
        this.data = data;
    }

    public Includes getIncludes() {
        return includes;
    }

    public void setIncludes(Includes includes) {
        this.includes = includes;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Media> getMedia() {
        return includes == null ? List.of() : includes.getMedia();
    }
}

