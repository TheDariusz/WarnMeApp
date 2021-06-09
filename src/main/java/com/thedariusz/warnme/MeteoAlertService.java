package com.thedariusz.warnme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class MeteoAlertService {

    private static final Logger logger = LoggerFactory.getLogger(MeteoAlertService.class);

    private static final String EMPTY_STRING = "";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final MeteoAlertDao meteoAlertDao;

    public MeteoAlertService(MeteoAlertDao meteoAlertDao) {
        this.meteoAlertDao = meteoAlertDao;
    }

    public void save(List<MeteoAlert> meteoAlerts) {
        final List<String> newIds = meteoAlerts.stream().
                map(MeteoAlert::getExternalId)
                .collect(Collectors.toList());

        List<MeteoAlert> existing = meteoAlertDao.fetchExisting(newIds);

        final List<MeteoAlert> alertsToSave = meteoAlerts.stream()
                .filter(meteoAlert -> externalIdEquals(existing, meteoAlert))
                .collect(Collectors.toList());

        logger.info("Saving {} new alerts", alertsToSave.size());
        alertsToSave.forEach(meteoAlertDao::save);
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
        return meteoAlertDao.getLatestCreatedAt()
                .map(DATE_TIME_FORMATTER::format)
                .orElse(EMPTY_STRING);
    }
}
