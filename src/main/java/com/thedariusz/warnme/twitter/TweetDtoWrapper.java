package com.thedariusz.warnme.twitter;

import com.thedariusz.warnme.twitter.model.Includes;
import com.thedariusz.warnme.twitter.model.Meta;

public class TweetDtoWrapper {
    TweetDto[] data;
    Includes includes;
    Meta meta;

    public TweetDtoWrapper(TweetDto[] data, Includes includes, Meta meta) {
        this.data = data;
        this.includes = includes;
        this.meta = meta;
    }

    public TweetDtoWrapper() {
    }

    public TweetDto[] getData() {
        return data;
    }

    public void setData(TweetDto[] data) {
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
}

