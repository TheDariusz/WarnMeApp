package com.thedariusz.warnme;

import com.thedariusz.warnme.twitter.MeteoAlert;
import com.thedariusz.warnme.twitter.TweetDto;
import com.thedariusz.warnme.twitter.model.Media;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TweetDtoMeteoAlertMapper {

    private static final String DIGIT_BEFORE_TEXT_OR_DEGREE_CHARACTER = "(\\d)\\s*(?=stopni|°)";

    private static final int LEVEL_NOT_FOUND = 0;

    private final MeteoAlertCategoryMapper meteoAlertCategoryMapper;

    public TweetDtoMeteoAlertMapper(MeteoAlertCategoryMapper meteoAlertCategoryMapper) {
        this.meteoAlertCategoryMapper = meteoAlertCategoryMapper;
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
        Pattern pattern = Pattern.compile(DIGIT_BEFORE_TEXT_OR_DEGREE_CHARACTER);
        Matcher matcher = pattern.matcher(textField);
        try {
            int alertLevel = matcher.find() ?
                    Integer.parseInt(matcher.group(1)) : LEVEL_NOT_FOUND;
            return alertLevel>0 && alertLevel<=3 ? alertLevel : LEVEL_NOT_FOUND;
        } catch (NumberFormatException e) {
            return LEVEL_NOT_FOUND;
        }
    }

    private Set<String> getAlertCategories(TweetDto tweetDto) {
        final Set<String> categoriesFromHashTags = meteoAlertCategoryMapper.getCategories(tweetDto.getHashtagsFromTweet());
        final Set<String> categoriesFromText = meteoAlertCategoryMapper.getCategoriesFromText(tweetDto.getText());

        return Stream.of(categoriesFromHashTags, categoriesFromText)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }



}
