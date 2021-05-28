package com.thedariusz.warnme.twitter.client;

import com.thedariusz.warnme.twitter.TweetDto;
import com.thedariusz.warnme.twitter.TweetDtoWrapper;
import com.thedariusz.warnme.twitter.TwitterClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.util.List;

public class SpringTwitterClient implements TwitterClient {

    private final WebClient webClient;

    public SpringTwitterClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<TweetDto> fetchAllTweets(String twitterUserId) {
        return List.of();
    }

    @Override
    public Disposable getSingleTweetDispose(String tweetId) {
        return webClient.get()
                .uri("/tweets/" + tweetId + "?tweet.fields=created_at,id,text")
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new RuntimeException("API not found")))
                .onStatus(HttpStatus::is5xxServerError,
                        error -> Mono.error(new RuntimeException("Server is not responding")))
                .bodyToMono(String.class)
                .subscribe((body)-> { System.out.println("body:" + body);} );
    }

    @Override
    public TweetDto getSingleTweetDto(String tweetId) {

        TweetDtoWrapper tweetDtoWrapper = webClient.get()
                .uri("/tweets/" + tweetId + "?tweet.fields=created_at,id,text")
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new RuntimeException("API not found")))
                .onStatus(HttpStatus::is5xxServerError,
                        error -> Mono.error(new RuntimeException("Server is not responding")))
                .bodyToMono(TweetDtoWrapper.class)
                .block();
        return tweetDtoWrapper.getData();
    }

}
