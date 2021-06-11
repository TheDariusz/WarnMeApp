package com.thedariusz.warnme;

import com.thedariusz.warnme.twitter.model.Media;
import com.thedariusz.warnme.twitter.model.TweetDto;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MeteoAlertGenericMapper {

    private static final String DIGIT_BEFORE_TEXT_OR_DEGREE_OR_DOT_CHARACTER = "(\\d)\\s*(?=stopni|°|\\.)";

    private static final int LEVEL_NOT_FOUND = 0;
    private static final int ALERT_MIN_LEVEL = 0;
    private static final int ALERT_MAX_LEVEL = 3;

    private final MeteoAlertCategoryUtil meteoAlertCategoryUtil;

    public MeteoAlertGenericMapper(MeteoAlertCategoryUtil meteoAlertCategoryUtil) {
        this.meteoAlertCategoryUtil = meteoAlertCategoryUtil;
    }

    public MeteoAlert mapToMeteoAlertFromTweet(TweetDto tweetDto, List<Media> media) {
        return new MeteoAlert(
                getAlertLevelFromTextField(tweetDto.getText()),
                getAlertCategories(tweetDto),
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

    private Set<String> getAlertCategories(TweetDto tweetDto) {
        final Set<String> categoriesFromHashTags = meteoAlertCategoryUtil.getCategoriesFromTags(tweetDto.getHashtagsFromTweet());
        final Set<String> categoriesFromText = meteoAlertCategoryUtil.getCategoriesFromText(tweetDto.getText());

        return Stream.of(categoriesFromHashTags, categoriesFromText)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }


}
