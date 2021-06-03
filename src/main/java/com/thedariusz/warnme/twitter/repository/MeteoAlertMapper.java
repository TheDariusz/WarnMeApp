package com.thedariusz.warnme.twitter.repository;

import com.thedariusz.warnme.twitter.MeteoAlert;
import com.thedariusz.warnme.twitter.MeteoAlertEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MeteoAlertMapper {

    private final MeteoAlertOriginMapper meteoAlertOriginMapper = new MeteoAlertOriginMapper();

    public MeteoAlert toModel(MeteoAlertEntity entity) {
        Set<String> categories = Arrays.stream(entity.getCategories().split(","))
                .collect(Collectors.toSet());

        List<String> media = Arrays.stream(entity.getMedia().split(","))
                .collect(Collectors.toList());

        return new MeteoAlert(
                entity.getLevel(),
                categories,
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
                String.join(",", categories),
                model.getCreationDate(),
                model.getDescription(),
                model.getExternalId(),
                String.join(",", media),
                meteoAlertOriginMapper.toEntity(model.getMeteoAlertOrigin())
        );
    }

}
