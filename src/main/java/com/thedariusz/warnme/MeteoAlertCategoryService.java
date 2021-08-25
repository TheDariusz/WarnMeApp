package com.thedariusz.warnme;

import com.thedariusz.warnme.repository.entity.MeteoAlertCategoryEntity;
import com.thedariusz.warnme.twitter.model.TweetDto;

import java.util.List;
import java.util.Set;

public interface MeteoAlertCategoryService {
    Set<String> getCategoriesFromTags(List<String> hashTags);
    Set<String> getCategoriesFromText(String text);
    Set<String> getAlertCategories(TweetDto tweetDto);
    Set<MeteoAlertCategoryEntity> findCategories(Set<String> name);
    MeteoAlertCategoryEntity saveCategory(MeteoAlertCategoryEntity categoryEntity);
    Set<MeteoAlertCategoryEntity> mapToEntities(Set<String> categories);
}
