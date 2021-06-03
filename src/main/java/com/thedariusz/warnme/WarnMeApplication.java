package com.thedariusz.warnme;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thedariusz.warnme.twitter.TweetService;
import com.thedariusz.warnme.twitter.TwitterClient;
import com.thedariusz.warnme.twitter.client.FakeTwitterClient;
import com.thedariusz.warnme.twitter.client.SpringTwitterClient;
import com.thedariusz.warnme.twitter.repository.MeteoAlertSpringDao;
import com.thedariusz.warnme.twitter.repository.PostgresMeteoAlertDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.ZoneOffset;
import java.util.TimeZone;

@SpringBootApplication
public class WarnMeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarnMeApplication.class, args);
	}

	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		objectMapper.setTimeZone(TimeZone.getTimeZone(ZoneOffset.UTC));
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		return objectMapper;
	}

	@Bean
	public MeteoAlertDao postgresMeteoAlertDao(MeteoAlertSpringDao meteoAlertSpringDao) {
		return new PostgresMeteoAlertDao(meteoAlertSpringDao);
	}

	@Bean
	public MeteoAlertService meteoAlertService(MeteoAlertDao meteoAlertDao) {
		return new MeteoAlertService(meteoAlertDao);
	}

	@Bean
	public TwitterClient fakeTwitterClient() {
		return new FakeTwitterClient();
	}

	@Bean
	@Primary
	public TwitterClient springTwitterClient(WebClient webClient) {
		return new SpringTwitterClient(webClient);
	}

	@Bean
	public TweetService tweetService(MeteoAlertService meteoAlertService, TwitterClient twitterClient) {
		return new TweetService(meteoAlertService, twitterClient, new TweetDtoMeteoAlertMapper(new MeteoAlertCategoryMapper()));
	}

}
