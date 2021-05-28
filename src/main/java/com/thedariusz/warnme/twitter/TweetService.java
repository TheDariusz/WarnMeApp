package com.thedariusz.warnme.twitter;

import com.thedariusz.warnme.MeteoAlertMapper;
import com.thedariusz.warnme.MeteoAlertService;
import reactor.core.Disposable;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TweetService {

    enum TweetType {
        METEO,
        METEO_ALERT,
        OTHER
    }

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
    private final MeteoAlertMapper meteoAlertMapper;

    public TweetService(MeteoAlertService meteoAlertService, TwitterClient twitterClient, MeteoAlertMapper meteoAlertMapper) {
        this.meteoAlertService = meteoAlertService;
        this.twitterClient = twitterClient;
        this.meteoAlertMapper = meteoAlertMapper;
    }

    public void syncTweets(String twitterUserId) {
        List<TweetDto> allTweets = twitterClient.fetchAllTweets(twitterUserId);

        List<MeteoAlert> meteoAlerts = allTweets.stream()
                .filter(this::isMeteoAlert)
                .map(meteoAlertMapper::mapToMeteoAlertFromTweet)
                .collect(Collectors.toList());

        meteoAlertService.save(meteoAlerts);
    }

    boolean isMeteoAlert(TweetDto tweetDto) {
        TweetType tweetType = getTweetTypeBasedOnHashTags(tweetDto.getHashTags());
        return tweetType.equals(TweetType.METEO_ALERT);
    }

    TweetType getTweetTypeBasedOnHashTags(List<String> hashTags) {

        TweetType tweetType = hashTags
                .stream()
                .map(String::toLowerCase)
                .anyMatch(getMeteoKeywords()::contains) ? TweetType.METEO : TweetType.OTHER;

        boolean hasMeteoAlertKeywords = hashTags
                .stream()
                .map(String::toLowerCase)
                .anyMatch(getMeteoAlertKeywords()::contains);

        if (tweetType.equals(TweetType.METEO) && hasMeteoAlertKeywords) {
            tweetType = TweetType.METEO_ALERT;
        }
        return tweetType;
    }

    private Set<String> getMeteoKeywords() {
        return TWEET_TYPE_TO_KEYWORDS.get(TweetType.METEO);
    }

    private Set<String> getMeteoAlertKeywords() {
        return TWEET_TYPE_TO_KEYWORDS.get(TweetType.METEO_ALERT);
    }

    public TweetDto getSingleTweet(String tweetId) {
        TweetDto singleTweet = twitterClient.getSingleTweetDto(tweetId);
//        Disposable singleTweet = twitterClient.getSingleTweetDispose(tweetId);
        return null;
    }
}
