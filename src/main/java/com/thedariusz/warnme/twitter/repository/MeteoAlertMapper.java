package com.thedariusz.warnme.twitter.repository;

import com.thedariusz.warnme.MeteoAlertCategory;
import com.thedariusz.warnme.MeteoAlertCategoryEntity;
import com.thedariusz.warnme.twitter.MeteoAlert;
import com.thedariusz.warnme.twitter.MeteoAlertEntity;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MeteoAlertMapper {

    private final MeteoAlertOriginMapper meteoAlertOriginMapper = new MeteoAlertOriginMapper();
    private final MeteoAlertCategoryMapper meteoAlertCategoryMapper = new MeteoAlertCategoryMapper();

    public MeteoAlert toModel(MeteoAlertEntity entity) {
        List<String> media = Arrays.stream(entity.getMedia().split(","))
                .collect(Collectors.toList());

        final Set<MeteoAlertCategoryEntity> entityCategories = entity.getCategories();

        return new MeteoAlert(
                entity.getLevel(),
                meteoAlertCategoryMapper.toModel(entityCategories),
                entity.getCreationDate(),
                entity.getDescription(),
                entity.getExternalId(),
                media,
                meteoAlertOriginMapper.toModel(entity.getMeteoAlertOriginEntity())
        );
    }

    public MeteoAlertEntity toEntity(MeteoAlert model) {
        final List<String> media = model.getMedia();
        final Set<String> categories = model.getCategories();

        return new MeteoAlertEntity(
                model.getLevel(),
                meteoAlertCategoryMapper.toEntity(categories),
                model.getCreationDate(),
                model.getDescription(),
                model.getExternalId(),
                String.join(",", media),
                meteoAlertOriginMapper.toEntity(model.getMeteoAlertOrigin()),
                OffsetDateTime.now()
        );
    }

}
