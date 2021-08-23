package com.thedariusz.warnme;

import com.thedariusz.warnme.twitter.model.Media;
import com.thedariusz.warnme.twitter.model.TweetDto;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MeteoAlertGenericMapper {

    private static final String DIGIT_BEFORE_TEXT_OR_DEGREE_OR_DOT_CHARACTER = "(\\d)\\s*(?=stopni|°|\\.)";

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

    private int getAlertLevelFromTextField(String textField) {
        Pattern pattern = Pattern.compile(DIGIT_BEFORE_TEXT_OR_DEGREE_OR_DOT_CHARACTER);
        Matcher matcher = pattern.matcher(textField);
        try {
            int alertLevel = matcher.find()
                    ? Integer.parseInt(matcher.group(1)):LEVEL_NOT_FOUND;
            return alertLevel > ALERT_MIN_LEVEL && alertLevel <= ALERT_MAX_LEVEL ? alertLevel:LEVEL_NOT_FOUND;
        } catch (NumberFormatException e) {
            return LEVEL_NOT_FOUND;
        }
    }


}
