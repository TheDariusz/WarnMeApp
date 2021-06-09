package com.thedariusz.warnme.repository.entity;

import com.thedariusz.warnme.MeteoAlertOrigin;

public class MeteoAlertOriginMapper {

    public MeteoAlertOriginEntity toEntity(MeteoAlertOrigin meteoAlertOrigin) {
        return new MeteoAlertOriginEntity(
                meteoAlertOrigin.getSourceName(),
                meteoAlertOrigin.getSourceAuthorId(),
                meteoAlertOrigin.getOriginalId()
        );
    }

    public MeteoAlertOrigin toModel(MeteoAlertOriginEntity entity) {
        return MeteoAlertOrigin.twitter(entity.getSourceAuthorId(), entity.getOriginalId());
    }
}
