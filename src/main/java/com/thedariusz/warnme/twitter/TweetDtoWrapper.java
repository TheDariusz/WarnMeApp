package com.thedariusz.warnme.twitter;

public class TweetDtoWrapper {
    TweetDto data;

    public TweetDto getData() {
        return data;
    }

    public void setData(TweetDto data) {
        this.data = data;
    }

    public TweetDtoWrapper() {
    }

    public TweetDtoWrapper(TweetDto data) {
        this.data = data;
    }
}
