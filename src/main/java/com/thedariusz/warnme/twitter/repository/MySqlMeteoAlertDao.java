package com.thedariusz.warnme.twitter.repository;

import com.thedariusz.warnme.MeteoAlertDao;
import com.thedariusz.warnme.twitter.MeteoAlert;

import java.util.List;

public class MySqlMeteoAlertDao implements MeteoAlertDao {

    @Override
    public void save(MeteoAlert meteoAlert) {

    }

    @Override
    public boolean exists(MeteoAlert meteoAlert) {
        return false;
    }

    @Override
    public boolean existsByExternalId(MeteoAlert meteoAlert) {
        return false;
    }

    @Override
    public List<MeteoAlert> fetchAll() {
        return List.of();
    }

    @Override
    public void deleteAll() {
        //skip this
    }

}
