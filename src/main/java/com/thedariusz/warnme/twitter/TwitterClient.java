package com.thedariusz.warnme.twitter;

import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TwitterClient {

    TweetDtoWrapper fetchAllTweets(String twitterUserId);
    TweetDto getSingleTweetDto(String tweetId);


}
