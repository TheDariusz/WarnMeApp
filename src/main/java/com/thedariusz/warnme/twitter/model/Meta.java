package com.thedariusz.warnme.twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Meta {

    private String oldestId;
    private String newestId;
    private String resultCount;
    private String nextToken;

    public Meta(String oldestId, String newestId, String resultCount, String nextToken) {
        this.oldestId = oldestId;
        this.newestId = newestId;
        this.resultCount = resultCount;
        this.nextToken = nextToken;
    }

    public Meta() {
    }

    public String getOldestId() {
        return oldestId;
    }

    @JsonProperty("oldest_id")
    public void setOldestId(String oldestId) {
        this.oldestId = oldestId;
    }

    public String getNewestId() {
        return newestId;
    }

    @JsonProperty("newest_id")
    public void setNewestId(String newestId) {
        this.newestId = newestId;
    }

    public String getResultCount() {
        return resultCount;
    }

    @JsonProperty("result_count")
    public void setResultCount(String resultCount) {
        this.resultCount = resultCount;
    }

    public String getNextToken() {
        return nextToken;
    }

    @JsonProperty("next_token")
    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }
}
