package com.thedariusz.warnme.twitter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thedariusz.warnme.twitter.model.Entity;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TweetDto {
    private String id;
    private String text;
    private String author_id;
    private String created_at;
    private Entity entities;

    public TweetDto(String id,
                    String text,
                    String author_id,
                    String created_at,
                    Entity entity) {
        this.id = id;
        this.text = text;
        this.author_id = author_id;
        this.created_at = created_at;
        this.entities = entity;
    }

    public TweetDto() {
    }
    public static TweetDtoBuilder builder() {
        return new TweetDtoBuilder();
    }

    public static final class TweetDtoBuilder {
        private String id;
        private String text;
        private String author_id;
        private String created_at;
        private Entity entity;

        private TweetDtoBuilder() {
        }

        public TweetDtoBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public TweetDtoBuilder withText(String text) {
            this.text = text;
            return this;
        }

        public TweetDtoBuilder withAuthorId(String authorId) {
            this.author_id = authorId;
            return this;
        }

        public TweetDtoBuilder withCreationDate(String creationDate) {
            this.created_at = creationDate;
            return this;
        }

        public TweetDtoBuilder withEntity(Entity entity) {
            this.entity = entity;
            return this;
        }

        public TweetDto build() {
            return new TweetDto(id, text, author_id, created_at, entity);
        }

        public TweetDto fakeTweet(String id, String creationDate, String twitterUserId, Entity entity, String text) {
            return new TweetDto(id, text, twitterUserId, creationDate,  entity);
        }
    }
    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getCreationDate() {
        return created_at;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public Entity getEntities() {
        return entities;
    }

    public void setEntities(Entity entities) {
        this.entities = entities;
    }

    @Override
    public String toString() {
        return "TweetDto{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", author_id='" + author_id + '\'' +
                ", created_at='" + created_at + '\'' +
                ", entities=" + entities +
                '}';
    }
}
