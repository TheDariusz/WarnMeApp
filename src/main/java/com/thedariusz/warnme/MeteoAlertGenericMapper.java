package com.thedariusz.warnme;

import com.google.common.annotations.VisibleForTesting;
import com.thedariusz.warnme.twitter.model.Media;
import com.thedariusz.warnme.twitter.model.TweetDto;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MeteoAlertGenericMapper {

    private static final String DIGIT_BEFORE_TEXT_OR_DEGREE_OR_DOT_CHARACTER = "(\\d)\\s*(?=stopni|°|⁰|\\.)";
    private static final String DIGIT_AFTER_TEXT = "(?<=st.|stopień)\\s*(\\d)";

    private static final int LEVEL_NOT_FOUND = 0;
    private static final int ALERT_MIN_LEVEL = 0;
    private static final int ALERT_MAX_LEVEL = 3;

    private final MeteoAlertCategoryService categoryService;

    public MeteoAlertGenericMapper(MeteoAlertCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public MeteoAlert mapToMeteoAlertFromTweet(TweetDto tweetDto, List<Media> media) {
        return new MeteoAlert(
                getAlertLevelFromTextField(tweetDto.getText()),
                categoryService.getAlertCategories(tweetDto),
                tweetDto.getCreatedAt(),
                tweetDto.getText(),
                tweetDto.getId(),
                createMedia(tweetDto, media),
                createMeteoAlertOrigin(tweetDto)
        );
    }

    private List<String> createMedia(TweetDto tweetDto, List<Media> media) {
        List<String> mediaKeys = tweetDto.getMediaKeys();
        return media.stream()
                .filter(m -> mediaKeys.contains(m.getMediaKey()))
                .map(Media::getUrl)
                .collect(Collectors.toList());
    }

    private MeteoAlertOrigin createMeteoAlertOrigin(TweetDto tweetDto) {
        return MeteoAlertOrigin.twitter(tweetDto.getAuthorId(), tweetDto.getId());
    }

    int getAlertLevelFromTextField(String textField) {
        int alertLevel = LEVEL_NOT_FOUND;

        Pattern pattern1 = Pattern.compile(DIGIT_BEFORE_TEXT_OR_DEGREE_OR_DOT_CHARACTER);
        Pattern pattern2 = Pattern.compile(DIGIT_AFTER_TEXT);
        Matcher matcher1 = pattern1.matcher(textField);
        Matcher matcher2 = pattern2.matcher(textField);

        try {
            if (matcher1.find()) {
                alertLevel = Integer.parseInt(matcher1.group(1));
            } else if (matcher2.find()){
                alertLevel = Integer.parseInt(matcher2.group(1));
            }
        } catch (NumberFormatException e) {
            return alertLevel;
        }

        return alertLevel > ALERT_MIN_LEVEL && alertLevel <= ALERT_MAX_LEVEL ? alertLevel : LEVEL_NOT_FOUND;
    }


}
