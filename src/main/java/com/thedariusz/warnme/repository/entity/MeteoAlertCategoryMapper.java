package com.thedariusz.warnme.repository.entity;

import java.util.Set;
import java.util.stream.Collectors;

public class MeteoAlertCategoryMapper {
    public Set<String> toModelSet(Set<MeteoAlertCategoryEntity> entities) {
        return entities.stream()
                .map(MeteoAlertCategoryEntity::getName)
                .collect(Collectors.toSet());
    }

    public Set<MeteoAlertCategoryEntity> toEntitySet(Set<String> categories) {
        return categories.stream()
                .map(MeteoAlertCategoryEntity::new)
                .collect(Collectors.toSet());
    }

    public MeteoAlertCategoryEntity toEntity(String categoryName) {
        return new MeteoAlertCategoryEntity(categoryName);
    }
}
