package com.thedariusz.warnme;

import com.thedariusz.warnme.twitter.MeteoAlert;

import java.util.List;
import java.util.stream.Collectors;

public class MeteoAlertService {

    private final MeteoAlertDao meteoAlertDao;

    public MeteoAlertService(MeteoAlertDao meteoAlertDao) {
        this.meteoAlertDao = meteoAlertDao;
    }

    public void save(List<MeteoAlert> meteoAlerts) {
        List<MeteoAlert> existing = meteoAlertDao.fetchExisting(
                meteoAlerts.stream()
                        .map(MeteoAlert::getExternalId)
                        .collect(Collectors.toList()));
        meteoAlerts.stream()
                .filter(meteoAlert -> externalIdEquals(existing, meteoAlert))
                .forEach(meteoAlertDao::save);
    }

    public List<MeteoAlert> getMeteoAlertsFromDb() {
        return meteoAlertDao.fetchAll();
    }

    private boolean externalIdEquals(List<MeteoAlert> existing, MeteoAlert meteoAlert) {
        for(MeteoAlert alert : existing) {
            if (alert.getExternalId().equals(meteoAlert.getExternalId())) {
                return false;
            }
        }
        return true;
    }
}
