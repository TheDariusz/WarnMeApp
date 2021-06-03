package com.thedariusz.warnme.twitter.repository;

import com.thedariusz.warnme.MeteoAlertOrigin;
import com.thedariusz.warnme.MeteoAlertOriginEntity;

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
