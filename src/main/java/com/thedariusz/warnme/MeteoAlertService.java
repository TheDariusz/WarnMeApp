package com.thedariusz.warnme;

import com.thedariusz.warnme.twitter.MeteoAlert;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;

public class MeteoAlertService {

    private final MeteoAlertDao meteoAlertDao;

    public MeteoAlertService(MeteoAlertDao meteoAlertDao) {
        this.meteoAlertDao = meteoAlertDao;
    }

    public void save(List<MeteoAlert> meteoAlerts) {
        final List<String> newIds = meteoAlerts.stream().
                map(MeteoAlert::getExternalId)
                .collect(Collectors.toList());

        List<MeteoAlert> existing = meteoAlertDao.fetchExisting(newIds);

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

    public String getRefreshDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        return formatter.format(meteoAlertDao.getLatestRecordDateTime());
    }
}
