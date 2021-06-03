package com.thedariusz.warnme.twitter;

import com.thedariusz.warnme.TweetDtoMeteoAlertMapper;
import com.thedariusz.warnme.MeteoAlertService;
import com.thedariusz.warnme.twitter.model.Hashtag;
import com.thedariusz.warnme.twitter.model.Media;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

public class TweetService {

    enum TweetType {
        METEO,
        METEO_ALERT,
        OTHER
    }
    private static final Logger logger = LoggerFactory.getLogger(TweetService.class);
    private static final Map<TweetType, Set<String>> TWEET_TYPE_TO_KEYWORDS = Map.of(
            TweetType.METEO, Set.of("meteo", "weather", "pogoda", "burze", "burza",
                    "upał", "mróz", "meteoimgw", "przymrozki", "temperatura", "hydro",
                    "deszcz", "wichura", "grad", "ulewa",
                    "śnieg", "prognoza"),

            TweetType.METEO_ALERT, Set.of("ostrzegamy", "ostrzeżenia", "ostrzeżenie", "alert",
                    "meteoalert", "uwaga", "alertrcb", "burzaalert")
    );

    private final MeteoAlertService meteoAlertService;
    private final TwitterClient twitterClient;
    private final TweetDtoMeteoAlertMapper tweetDtoMeteoAlertMapper;

    public TweetService(MeteoAlertService meteoAlertService, TwitterClient twitterClient, TweetDtoMeteoAlertMapper tweetDtoMeteoAlertMapper) {
        this.meteoAlertService = meteoAlertService;
        this.twitterClient = twitterClient;
        this.tweetDtoMeteoAlertMapper = tweetDtoMeteoAlertMapper;
    }

    public void syncTweets(String twitterUserId) {
        TweetDtoWrapper tweetDtoWrapper = twitterClient.fetchAllTweets(twitterUserId);
        List<Media> media = tweetDtoWrapper.getMedia();

        List<TweetDto> allTweetsBody = tweetDtoWrapper.getData();

        List<MeteoAlert> meteoAlerts = allTweetsBody.stream()
                .filter(this::isMeteoAlert)
                .map(tweetDto -> tweetDtoMeteoAlertMapper.mapToMeteoAlertFromTweet(tweetDto, media))
                .collect(Collectors.toList());

        meteoAlertService.save(meteoAlerts);
    }

    boolean isMeteoAlert(TweetDto tweetDto) {
        TweetType tweetTypeFromTags = getTweetTypeBasedOnHashTags(tweetDto.getHashtagsFromTweet());
        TweetType tweetTypeFromText = getTweetTypeBasedOnTweetText(tweetDto.getText());

        return tweetTypeFromTags.equals(TweetType.METEO_ALERT) ||
                tweetTypeFromText.equals(TweetType.METEO_ALERT);
    }

    TweetType getTweetTypeBasedOnHashTags(List<Hashtag> hashTags) {
        if (isEmpty(hashTags)) {
            return TweetType.OTHER;
        }

        TweetType tweetType = hashTags
                .stream()
                .map(Hashtag::getTag)
                .map(String::toLowerCase)
                .anyMatch(getMeteoKeywords()::contains) ? TweetType.METEO : TweetType.OTHER;

        boolean hasMeteoAlertKeywords = hashTags
                .stream()
                .map(Hashtag::getTag)
                .map(String::toLowerCase)
                .anyMatch(getMeteoAlertKeywords()::contains);

        if (tweetType.equals(TweetType.METEO) && hasMeteoAlertKeywords) {
            tweetType = TweetType.METEO_ALERT;
        }
        return tweetType;
    }

    private TweetType getTweetTypeBasedOnTweetText(String text) {
        if (text.isBlank()) {
            return TweetType.OTHER;
        }

        String lowerCaseText = text.toLowerCase();

        TweetType tweetType = getMeteoKeywords().stream()
                .anyMatch(lowerCaseText::contains) ? TweetType.METEO:TweetType.OTHER;

        if (TweetType.METEO.equals(tweetType)) {
            tweetType = getMeteoAlertKeywords().stream()
                    .anyMatch(lowerCaseText::contains) ? TweetType.METEO_ALERT : tweetType;
        }

        return tweetType;
    }

    private Set<String> getMeteoKeywords() {
        return TWEET_TYPE_TO_KEYWORDS.get(TweetType.METEO);
    }

    private Set<String> getMeteoAlertKeywords() {
        return TWEET_TYPE_TO_KEYWORDS.get(TweetType.METEO_ALERT);
    }

}
