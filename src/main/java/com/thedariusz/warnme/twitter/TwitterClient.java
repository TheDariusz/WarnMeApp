package com.thedariusz.warnme.twitter;

public interface TwitterClient {

    TweetDtoWrapper fetchAllTweets(String twitterUserId);
    TweetDto getSingleTweetDto(String tweetId);


}
