package com.thedariusz.warnme.api;

import com.thedariusz.warnme.twitter.model.Entity;

class IntegrationTestBase {

    protected static final String ALERTS_PATH = "/alerts";

    protected static class TweetDtoTest {
        public String id;
        public String text;
        public String author;
        public String creationDate;
        public Entity entity;

        public TweetDtoTest(String id, String text, String author, String creationDate, Entity entity) {
            this.id = id;
            this.text = text;
            this.author = author;
            this.creationDate = creationDate;
            this.entity = entity;
        }

        public TweetDtoTest() {}

        @Override
        public String toString() {
            return "TweetDtoTest{" +
                    "id='" + id + '\'' +
                    ", text='" + text + '\'' +
                    ", author='" + author + '\'' +
                    ", creationDate='" + creationDate + '\'' +
                    ", entity=" + entity +
                    '}';
        }
    }

}
