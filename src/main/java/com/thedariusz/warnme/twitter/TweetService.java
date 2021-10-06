package com.thedariusz.warnme.twitter;

import com.thedariusz.warnme.MeteoAlert;
import com.thedariusz.warnme.MeteoAlertService;
import com.thedariusz.warnme.MeteoAlertGenericMapper;
import com.thedariusz.warnme.twitter.model.Media;
import com.thedariusz.warnme.twitter.model.TweetDto;
import com.thedariusz.warnme.twitter.model.TweetDtoWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TweetService {

    private static final String EMPTY_VALUE = null;

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
    private final MeteoAlertGenericMapper meteoAlertGenericMapper;

    public TweetService(MeteoAlertService meteoAlertService, TwitterClient twitterClient, MeteoAlertGenericMapper meteoAlertGenericMapper) {
        this.meteoAlertService = meteoAlertService;
        this.twitterClient = twitterClient;
        this.meteoAlertGenericMapper = meteoAlertGenericMapper;
    }

    public void syncTweets(String twitterUserId) {
        //todo get last record date from DB as api start_date
        String startTime = "2021-09-01T00:00:00.00Z";

        ZonedDateTime dateTime = ZonedDateTime.now();
        String endTime=dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSXXX"));

        String paginationToken = EMPTY_VALUE;

//        TweetDtoWrapper tweetDtoWrapper = twitterClient.fetchAllTweets(twitterUserId);
        do {
            TweetDtoWrapper tweetDtoWrapper = twitterClient.fetchTweetsForUserAndSpecificTimePeriod(twitterUserId, startTime, endTime, paginationToken);

            List<Media> media = tweetDtoWrapper.getMedia();
            List<TweetDto> tweets = tweetDtoWrapper.getData();
            paginationToken = tweetDtoWrapper.getMeta().getNextToken();

//            List<MeteoAlert> meteoAlerts = tweets.stream()
//                    .filter(this::isMeteoAlert)
//                    .map(tweetDto -> meteoAlertGenericMapper.mapToMeteoAlertFromTweet(tweetDto, media))
//                    .collect(Collectors.toList());
//
//            logger.info("Fetched {} total alerts", meteoAlerts.size());
//            meteoAlertService.save(meteoAlerts);
//            logger.info("total tweets got: {}, pagination token: {}, total meteo alert got: {}", tweets.size(), paginationToken, meteoAlerts.size());
            logger.info("start={}, end={}, total tweets got: {}, pagination token: {}", startTime, endTime, tweets.size(), paginationToken);
        } while (paginationToken!=null);
    }

    boolean isMeteoAlert(TweetDto tweetDto) {
        TweetType tweetType = TweetType.OTHER;
        final List<String> hashtags = tweetDto.getHashtagsFromTweet();
        final String text = tweetDto.getText();

        if (tweetHashtagsContainsKeywords(hashtags, getMeteoKeywords())
                || tweetTextFieldContainsKeywords(text, getMeteoKeywords())) {
            tweetType = TweetType.METEO;
        }

        return tweetType.equals(TweetType.METEO) &&
                (tweetHashtagsContainsKeywords(hashtags, getMeteoAlertKeywords())
                        || tweetTextFieldContainsKeywords(text, getMeteoAlertKeywords()));
    }

    private boolean tweetHashtagsContainsKeywords(List<String> hashtagsFromTweet, Set<String> keywords) {
        return hashtagsFromTweet
                .stream()
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
