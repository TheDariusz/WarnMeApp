package com.thedariusz.warnme.twitter.model;

import com.thedariusz.warnme.twitter.model.Media;

public class Includes {
    Media[] media;

    public Media[] getMedia() {
        return media;
    }

    public void setMedia(Media[] media) {
        this.media = media;
    }

    public Includes(Media[] media) {
        this.media = media;
    }

    public Includes() {
    }
}
