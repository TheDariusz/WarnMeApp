package com.thedariusz.warnme.twitter;

import com.thedariusz.warnme.twitter.model.TweetDtoWrapper;

public interface TwitterClient {

    TweetDtoWrapper fetchAllTweets(String twitterUserId);

}
