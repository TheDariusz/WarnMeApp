package com.thedariusz.warnme.twitter;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class TwitterClientConfiguration {
    private static final String TWITTER_BASE_URL = "https://api.twitter.com/2";
    private static final int TIMEOUT = 5000;
    private static final String BEARER_TOKEN = "AAAAAAAAAAAAAAAAAAAAAFSPPAEAAAAAHjF8hhpwX2710wpG0NhX1doTCCs%3DKpdIuBZoPAOv6gnfMdFnniAMkP119yosEGjiiqS8aCywcfuszJ";

    @Bean
    WebClient twitterClientBuilder(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(TWITTER_BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + BEARER_TOKEN)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
            .responseTimeout(Duration.ofMillis(TIMEOUT))
            .doOnConnected(conn ->
                    conn.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS))
                            .addHandlerLast(new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS)));
}
