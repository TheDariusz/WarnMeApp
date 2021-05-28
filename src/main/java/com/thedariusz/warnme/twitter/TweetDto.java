package com.thedariusz.warnme.twitter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TweetDto {

    @JsonProperty("id")
    private String tweetId;
    private String text;
    private AuthorDto author;
    @JsonProperty("created_at")
    private String creationDate;
    private List<String> mediaList;
    private List<String> hashTags;

    public TweetDto(String tweetId,
                    String text,
                    AuthorDto author,
                    String creationDate,
                    List<String> mediaList,
                    List<String> hashTags) {
        this.tweetId = tweetId;
        this.text = text;
        this.author = author;
        this.creationDate = creationDate;
        this.mediaList = List.copyOf(mediaList);
        this.hashTags = List.copyOf(hashTags);
    }

    public TweetDto() {
    }
    public static TweetDtoBuilder builder() {
        return new TweetDtoBuilder();
    }

    public static final class TweetDtoBuilder {
        private String tweetId;
        private String text;
        private AuthorDto author;
        private String creationDate;
        private List<String> mediaList;
        private List<String> hashTags;

        private TweetDtoBuilder() {
        }

        public TweetDtoBuilder withTweetId(String tweetId) {
            this.tweetId = tweetId;
            return this;
        }

        public TweetDtoBuilder withText(String text) {
            this.text = text;
            return this;
        }

        public TweetDtoBuilder withAuthor(AuthorDto author) {
            this.author = author;
            return this;
        }

        public TweetDtoBuilder withCreationDate(String creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public TweetDtoBuilder withMediaList(List<String> mediaList) {
            this.mediaList = mediaList;
            return this;
        }

        public TweetDtoBuilder withHashTags(List<String> hashTags) {
            this.hashTags = hashTags;
            return this;
        }

        public TweetDto build() {
            return new TweetDto(tweetId, text, author, creationDate, mediaList, hashTags);
        }

        public TweetDto fakeTweet(String id, String creationDate, String twitterUserId, List<String> hashTags, String text) {
            return new TweetDto(id, text, AuthorDto.fake(twitterUserId), creationDate,  List.of("url1", "url2"), hashTags);
        }
    }
    public String getTweetId() {
        return tweetId;
    }

    public String getText() {
        return text;
    }

    public AuthorDto getAuthor() {
        return author;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public List<String> getMediaList() {
        return mediaList;
    }

    public List<String> getHashTags() {
        return hashTags;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAuthor(AuthorDto author) {
        this.author = author;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void setMediaList(List<String> mediaList) {
        this.mediaList = mediaList;
    }

    public void setHashTags(List<String> hashTags) {
        this.hashTags = hashTags;
    }
}
