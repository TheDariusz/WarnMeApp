package com.thedariusz.warnme.twitter.client;

import com.thedariusz.warnme.twitter.TweetDto;
import com.thedariusz.warnme.twitter.TweetDtoWrapper;
import com.thedariusz.warnme.twitter.TwitterClient;
import reactor.core.Disposable;

public class FakeTwitterClient implements TwitterClient {

    public TweetDtoWrapper fetchAllTweets(String twitterUserId) {
//        List<TweetDto> tweetDtos = new ArrayList<>();
//        if ("1139834822011084801".equals(twitterUserId)) {
//            tweetDtos =  List.of(
//                    TweetDto.builder().fakeTweet("1",  OffsetDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME), twitterUserId, List.of("ostrzeżenie", "podtopienia", "burze"),
//                            "Woj. śląskie ostrzeżenie 2 stopnia - Burze z gradem ️Prognozuje się wystąpienie burz z opadami \" +\n"
//                            + "\"deszczu miejscami od 30 mm do 50 mm, lokalnie do\\\\n60 mm oraz porywami wiatru do 80 km/h. \" +\n"
//                            + "\"\\\\nMiejscami grad. \\\\n\\\\n#IMGW #ostrzeżenie #burze #podtopienia https://t.co/vaYBh6XRaX\""),
//                    TweetDto.builder().fakeTweet("2",  OffsetDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME), twitterUserId, List.of("ostrzeżenia", "burze", "IMGW"),
//                            "️IMGW-PIB wydał #ostrzeżenia meteo 1° przed burzami z gradem dla woj. dolnośląskiego, \" +\n"
//                            + "        \"opolskiego, południowej części woj. małopolskiego, podkarpackiego i śląskiego. \" +\n"
//                            + "        \"Prognozuje się wystąpienie burz z opadami deszczu miejscami do 40 mm oraz porywami wiatru do 80 km/h.\\n#burze #IMGW https://t.co/dpkOHtEM9y"),
//                    TweetDto.builder().fakeTweet("3",  OffsetDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME), twitterUserId, List.of("wiosna"), "")
//            );
//        }
//        if ("2979632800".equals(twitterUserId)) {
//            tweetDtos =  List.of(
//                    TweetDto.builder().fakeTweet("10", "2021-05-11T11:10:10.000Z", twitterUserId, List.of("burze", "alertrcb"), ""),
//                    TweetDto.builder().fakeTweet("20", "2021-03-12T17:05:02.000Z", twitterUserId, List.of("upał", "alert"), ""),
//                    TweetDto.builder().fakeTweet("30", "2021-02-09T08:55:40.000Z", twitterUserId, List.of("strajk"), "")
//            );
//        }

        return null;
    }

    @Override
    public TweetDto getSingleTweetDto(String tweetId) {
        return null;
    }

}
