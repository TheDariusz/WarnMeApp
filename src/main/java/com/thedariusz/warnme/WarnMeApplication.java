package com.thedariusz.warnme;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thedariusz.warnme.repository.MeteoAlertCategoryJpaRepository;
import com.thedariusz.warnme.repository.MeteoAlertJpaRepository;
import com.thedariusz.warnme.repository.MeteoAlertRepository;
import com.thedariusz.warnme.repository.entity.MeteoAlertCategoryMapper;
import com.thedariusz.warnme.twitter.TweetService;
import com.thedariusz.warnme.twitter.TwitterClient;
import com.thedariusz.warnme.twitter.client.SpringTwitterClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.ZoneOffset;
import java.util.TimeZone;

@SpringBootApplication
@PropertySource(value = "file:${catalina.home}/conf/warnme-config.properties")
public class WarnMeApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WarnMeApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(WarnMeApplication.class);
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
	public MeteoAlertCategoryService meteoAlertCategoryService(MeteoAlertCategoryJpaRepository categoryRepository) {
		return new MeteoAlertTwitterCategoryService(categoryRepository, new MeteoAlertCategoryMapper());
	}

	@Bean
	public MeteoAlertDao postgresMeteoAlertDao(MeteoAlertJpaRepository meteoAlertSpringDao, MeteoAlertCategoryJpaRepository categoryRepository) {
		return new MeteoAlertRepository(meteoAlertSpringDao, categoryRepository);
	}

	@Bean
	public MeteoAlertService meteoAlertService(MeteoAlertDao meteoAlertDao) {
		return new MeteoAlertService(meteoAlertDao);
	}

	@Bean
	public TwitterClient springTwitterClient(WebClient webClient) {
		return new SpringTwitterClient(webClient);
	}

	@Bean
	public TweetService tweetService(MeteoAlertService meteoAlertService, TwitterClient twitterClient, MeteoAlertCategoryService categoryService) {
		return new TweetService(meteoAlertService, twitterClient, new MeteoAlertGenericMapper(categoryService));
	}

}
