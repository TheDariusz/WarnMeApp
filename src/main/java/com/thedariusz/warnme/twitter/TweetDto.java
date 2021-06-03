package com.thedariusz.warnme.twitter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thedariusz.warnme.twitter.model.Attachments;
import com.thedariusz.warnme.twitter.model.Entity;
import com.thedariusz.warnme.twitter.model.Hashtag;

import java.util.List;

public class TweetDto {

    private String id;
    private String text;
    private String authorId;
    private String createdAt;
    private Entity entities;
    private Attachments attachments;

    public static TweetDtoBuilder builder() {
        return new TweetDtoBuilder();
    }

    public TweetDto(String id,
                    String text,
                    String authorId,
                    String createdAt,
                    Entity entities,
                    Attachments attachments) {
        this.id = id;
        this.text = text;
        this.authorId = authorId;
        this.createdAt = createdAt;
        this.entities = entities;
        this.attachments = attachments;
    }

    public TweetDto() {
    }

   public List<String> getMediaKeys() {
        return attachments == null ? List.of() : attachments.getMediaKeys();
   }

    public static final class TweetDtoBuilder {
        private String id;
        private String text;
        private String authorId;
        private String createdAt;
        private Entity entity;
        private List<String> mediaKeys;

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
            this.authorId = authorId;
            return this;
        }

        public TweetDtoBuilder withCreationDate(String creationDate) {
            this.createdAt = creationDate;
            return this;
        }

        public TweetDtoBuilder withEntity(Entity entity) {
            this.entity = entity;
            return this;
        }

        public TweetDtoBuilder withMediaKeys(List<String> mediaKeys) {
            this.mediaKeys = mediaKeys;
            return this;
        }

        public TweetDto build() {
            return new TweetDto(id, text, authorId, createdAt, entity, new Attachments(mediaKeys));
        }
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("author_id")
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Entity getEntities() {
        return entities;
    }

    public List<Hashtag> getHashtagsFromTweet() {
        return entities.getHashtags();
    }

    public void setEntities(Entity entities) {
        this.entities = entities;
    }

    public Attachments getAttachments() {
        return attachments;
    }

    public void setAttachments(Attachments attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "TweetDto{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", authorId='" + authorId + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", entities=" + entities +
                ", attachments=" + attachments +
                '}';
    }
}
