package com.thedariusz.warnme;

import com.thedariusz.warnme.twitter.MeteoAlert;

import java.util.List;

public class MeteoAlertService {

    private final MeteoAlertDao meteoAlertDao;

    public MeteoAlertService(MeteoAlertDao meteoAlertDao) {
        this.meteoAlertDao = meteoAlertDao;
    }

    public void save(List<MeteoAlert> meteoAlerts) {
        meteoAlerts.stream()
                .filter(meteoAlert -> !meteoAlertDao.existsByExternalId(meteoAlert))
                .forEach(meteoAlertDao::save);
    }
}
