package com.thedariusz.warnme.twitter.model;

import java.util.List;

public class Includes {

    List<Media> media;

    public Includes(List<Media> media) {
        this.media = media;
    }

    public Includes() {
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }
}
