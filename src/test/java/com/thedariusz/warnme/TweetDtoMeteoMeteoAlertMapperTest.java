package com.thedariusz.warnme;

import com.thedariusz.warnme.twitter.model.Entity;
import com.thedariusz.warnme.twitter.model.Hashtag;
import com.thedariusz.warnme.twitter.MeteoAlert;
import com.thedariusz.warnme.twitter.TweetDto;
import com.thedariusz.warnme.twitter.model.Media;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class TweetDtoMeteoMeteoAlertMapperTest {

    MeteoAlertCategoryAssigment meteoAlertCategoryAssigment = new MeteoAlertCategoryAssigment();
    TweetDtoMeteoAlertMapper tweetDtoMeteoAlertMapper = new TweetDtoMeteoAlertMapper(meteoAlertCategoryAssigment);

    @Test
    void shouldMapToMeteoAlertWithLevelNotFound() {
        //given
        final List<String> exampleOfHashtags = List.of("burze", "wichura");
        List<Hashtag> hashtags = exampleOfHashtags.stream()
                .map(Hashtag::new)
                .collect(Collectors.toList());

        Entity entity = new Entity(null, hashtags);

        TweetDto tweetWithoutMeaningfulText = TweetDto.builder()
                .withId("1139834822011084801")
                .withText("test")
                .withAuthorId("1139834822011084801")
                .withCreationDate("2021-05-06T10:13:17.000Z")
                .withEntity(entity)
                .withMediaKeys(List.of("123"))
                .build();

        final Media media = new Media("photo", "123", "http://url.com");

        //when
        final MeteoAlert meteoAlert = tweetDtoMeteoAlertMapper.mapToMeteoAlertFromTweet(tweetWithoutMeaningfulText, List.of(media));

        //then
        final MeteoAlertOrigin twitterOrigin = MeteoAlertOrigin.twitter("1139834822011084801", "1139834822011084801");
        final MeteoAlert expectedAlertWithLeveNotFound = new MeteoAlert(0, Set.of("burze", "wichura"),
                "2021-05-06T10:13:17.000Z", "test", "1139834822011084801", List.of("http://url.com"), twitterOrigin);

        assertThat(meteoAlert)
                .usingRecursiveComparison()
                .isEqualTo(expectedAlertWithLeveNotFound);
    }


    @Test
    void shouldMapToMeteoAlertWithLevelOne() {
        //given
        final List<String> exampleOfHashtags = List.of("burze", "wichura");
        List<Hashtag> hashtags = exampleOfHashtags.stream()
                .map(Hashtag::new)
                .collect(Collectors.toList());

        Entity entity = new Entity(null, hashtags);

        TweetDto tweetDtoWithAllFields = TweetDto.builder()
                .withId("1139834822011084801")
                .withText("@IMGWmeteo wydał ostrzeżenia 1. i 2. stopnia przed")
                .withAuthorId("1139834822011084801")
                .withCreationDate("2021-05-06T10:13:17.000Z")
                .withEntity(entity)
                .withMediaKeys(List.of("123"))
                .build();

        final Media media = new Media("photo", "321", "http://url.com");

        //when
        final MeteoAlert meteoAlert = tweetDtoMeteoAlertMapper.mapToMeteoAlertFromTweet(tweetDtoWithAllFields, List.of(media));

        //then
        final MeteoAlertOrigin meteoAlertOrigin = MeteoAlertOrigin.twitter("1139834822011084801", "1139834822011084801");
        final MeteoAlert expectedAlertWithLevelOne = new MeteoAlert(1, Set.of("burze", "wichura"),
                "2021-05-06T10:13:17.000Z", "@IMGWmeteo wydał ostrzeżenia 1. i 2. stopnia przed", "1139834822011084801", List.of(), meteoAlertOrigin);

        assertThat(meteoAlert)
                .usingRecursiveComparison()
                .isEqualTo(expectedAlertWithLevelOne);
    }

}