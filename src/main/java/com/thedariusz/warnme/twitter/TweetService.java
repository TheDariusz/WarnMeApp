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
        TweetType tweetType = TweetType.OTHER;

        if (tweetHashtagsContainsKeywords(tweetDto.getHashtagsFromTweet(), getMeteoKeywords()) ||
                tweetTextFieldContainsKeywords(tweetDto.getText(), getMeteoKeywords())) {
            tweetType=TweetType.METEO;
        }

        return tweetType.equals(TweetType.METEO) && (
                tweetHashtagsContainsKeywords(tweetDto.getHashtagsFromTweet(), getMeteoAlertKeywords()) ||
                        tweetTextFieldContainsKeywords(tweetDto.getText(), getMeteoAlertKeywords()));
    }

    private boolean tweetHashtagsContainsKeywords(List<Hashtag> hashtagsFromTweet, Set<String> keywords) {
        return hashtagsFromTweet
                .stream()
                .map(Hashtag::getTag)
                .map(String::toLowerCase)
                .anyMatch(keywords::contains);
    }

    private boolean tweetTextFieldContainsKeywords(String text, Set<String> keywords) {
        String lowerCaseText = text.toLowerCase();
        return keywords.stream()
                .anyMatch(lowerCaseText::contains);
    }
    private Set<String> getMeteoKeywords() {
        return TWEET_TYPE_TO_KEYWORDS.get(TweetType.METEO);
    }

    private Set<String> getMeteoAlertKeywords() {
        return TWEET_TYPE_TO_KEYWORDS.get(TweetType.METEO_ALERT);
    }

}
