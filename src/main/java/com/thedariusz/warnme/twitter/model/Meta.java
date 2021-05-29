package com.thedariusz.warnme.twitter.model;

public class Meta {
    private String oldest_id;
    private String newest_id;
    private String result_count;
    private String next_token;

    public String getOldest_id() {
        return oldest_id;
    }

    public void setOldest_id(String oldest_id) {
        this.oldest_id = oldest_id;
    }

    public String getNewest_id() {
        return newest_id;
    }

    public void setNewest_id(String newest_id) {
        this.newest_id = newest_id;
    }

    public String getResult_count() {
        return result_count;
    }

    public void setResult_count(String result_count) {
        this.result_count = result_count;
    }

    public String getNext_token() {
        return next_token;
    }

    public void setNext_token(String next_token) {
        this.next_token = next_token;
    }

    public Meta(String oldest_id, String newest_id, String result_count, String next_token) {
        this.oldest_id = oldest_id;
        this.newest_id = newest_id;
        this.result_count = result_count;
        this.next_token = next_token;
    }

    public Meta() {
    }
}
