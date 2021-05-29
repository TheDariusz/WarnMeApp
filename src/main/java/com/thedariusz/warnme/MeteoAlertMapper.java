package com.thedariusz.warnme;

import com.thedariusz.warnme.twitter.MeteoAlert;
import com.thedariusz.warnme.twitter.TweetDto;

import java.util.Collection;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MeteoAlertMapper {


    private static final String DIGIT_BEFORE_TEXT_OR_DEGREE_CHARACTER = "(\\d)\\s*(?=stopni|°)";

    private static final int LEVEL_NOT_FOUND = 0;

    private final MeteoAlertCategoryMapper meteoAlertCategoryMapper;

    public MeteoAlertMapper(MeteoAlertCategoryMapper meteoAlertCategoryMapper) {
        this.meteoAlertCategoryMapper = meteoAlertCategoryMapper;
    }

    public MeteoAlert mapToMeteoAlertFromTweet(TweetDto tweetDto) {
        return new MeteoAlert(
                getAlertLevelFromTextField(tweetDto.getText()),
                getAlertCategories(tweetDto),
                tweetDto.getCreationDate(),
                tweetDto.getText(),
                new MeteoAlertOrigin("Twitter", tweetDto.getAuthor_id(), tweetDto.getId()),
                null //todo list of urls
        );
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
        final Set<String> categoriesFromHashTags = meteoAlertCategoryMapper.getCategories(tweetDto.getEntities().getHashtags());
        final Set<String> categoriesFromText = meteoAlertCategoryMapper.getCategoriesFromText(tweetDto.getText());

        return Stream.of(categoriesFromHashTags, categoriesFromText)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }



}
