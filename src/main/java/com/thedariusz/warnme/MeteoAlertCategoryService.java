package com.thedariusz.warnme;

import com.thedariusz.warnme.repository.entity.MeteoAlertCategoryEntity;
import com.thedariusz.warnme.twitter.model.TweetDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MeteoAlertCategoryService {
    Set<String> getCategoriesFromTags(List<String> hashTags);
    Set<String> getCategoriesFromText(String text);
    Set<String> getAlertCategories(TweetDto tweetDto);
    Optional<MeteoAlertCategoryEntity> getCategoryEntityByName(String categoryName);
    MeteoAlertCategoryEntity saveNewAlertCategory(String categoryName);
}
