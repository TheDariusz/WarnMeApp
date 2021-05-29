package com.thedariusz.warnme;

import com.thedariusz.warnme.twitter.model.Entity;
import com.thedariusz.warnme.twitter.model.Hashtag;
import com.thedariusz.warnme.twitter.MeteoAlert;
import com.thedariusz.warnme.twitter.TweetDto;
import com.thedariusz.warnme.twitter.model.Url;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class MeteoAlertMapperTest {

    MeteoAlertCategoryMapper meteoAlertCategoryMapper = new MeteoAlertCategoryMapper();
    MeteoAlertMapper meteoAlertMapper = new MeteoAlertMapper(meteoAlertCategoryMapper);

    @Test
    void shouldMapToMeteoAlertWithLevelNotFound() {
        //given
        final String[] exampleOfHashtags = {"burze", "wichura"};
        Hashtag[] hashtags = Arrays.stream(exampleOfHashtags)
                .map(Hashtag::new)
                .toArray(Hashtag[]::new);
        Entity entity = new Entity(new Url[]{}, hashtags);

        TweetDto tweetWithoutMeaningfulText = TweetDto.builder()
                .withId("1")
                .withText("test")
                .withAuthorId("1139834822011084801")
                .withCreationDate("2021-05-06T10:13:17.000Z")
                .withEntity(entity)
                .build();

        //when
        final MeteoAlert meteoAlert = meteoAlertMapper.mapToMeteoAlertFromTweet(tweetWithoutMeaningfulText);

        //then
        final MeteoAlertOrigin meteoAlertOrigin = new MeteoAlertOrigin("Twitter", "1139834822011084801", "1");
        final MeteoAlert expectedAlertWithLeveNotFound = new MeteoAlert(0, Set.of("burze", "wichura"),
                "2021-05-06T10:13:17.000Z", "test", meteoAlertOrigin, null);

        assertThat(meteoAlert)
                .usingRecursiveComparison()
                .isEqualTo(expectedAlertWithLeveNotFound);
    }


    @Test
    void shouldMapToMeteoAlertWithLevelOne() {
        //given
        final String[] exampleOfHashtags = {"burze", "wichura"};
        Hashtag[] hashtags = Arrays.stream(exampleOfHashtags)
                .map(Hashtag::new)
                .toArray(Hashtag[]::new);
        Entity entity = new Entity(new Url[]{}, hashtags);

        TweetDto tweetDtoWithAllFields = TweetDto.builder()
                .withId("1")
                .withText("1 stopnia")
                .withAuthorId("1139834822011084801")
                .withCreationDate("2021-05-06T10:13:17.000Z")
                .withEntity(entity)
                .build();

        //when
        final MeteoAlert meteoAlert = meteoAlertMapper.mapToMeteoAlertFromTweet(tweetDtoWithAllFields);

        //then
        final MeteoAlertOrigin meteoAlertOrigin = new MeteoAlertOrigin("Twitter", "1139834822011084801", "1");
        final MeteoAlert expectedAlertWithLevelOne = new MeteoAlert(1, Set.of("burze", "wichura"),
                "2021-05-06T10:13:17.000Z", "1 stopnia", meteoAlertOrigin, null);

        assertThat(meteoAlert)
                .usingRecursiveComparison()
                .isEqualTo(expectedAlertWithLevelOne);
    }

}