package com.thedariusz.warnme.twitter.repository;

import com.thedariusz.warnme.twitter.MeteoAlert;
import com.thedariusz.warnme.MeteoAlertDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryMeteoAlertDao implements MeteoAlertDao {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryMeteoAlertDao.class);

    private static final List<MeteoAlert> DB_METEO_ALERTS = new ArrayList<>();

    @Override
    public void save(MeteoAlert meteoAlert) {
        logger.info("\nSaving to DB: -----------------------------------\n{}", meteoAlert);
        DB_METEO_ALERTS.add(meteoAlert);
    }

    @Override
    public boolean exists(MeteoAlert meteoAlert) {
        return true;
    }

    @Override
    public boolean existsByExternalId(MeteoAlert newAlert) {
        for (MeteoAlert meteoAlert : DB_METEO_ALERTS) {
            String alertOriginalId = newAlert.getAlertOriginalId();
            if (meteoAlert.getAlertOriginalId().equals(alertOriginalId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<MeteoAlert> fetchAll() {
        return DB_METEO_ALERTS;
    }

    @Override
    public void deleteAll() {
        DB_METEO_ALERTS.clear();
    }


}
