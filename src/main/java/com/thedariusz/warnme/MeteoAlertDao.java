package com.thedariusz.warnme;

import com.thedariusz.warnme.twitter.MeteoAlert;

import java.util.List;

public interface MeteoAlertDao {

    void save(MeteoAlert meteoAlert);

    boolean exists(MeteoAlert meteoAlert);

    boolean existsByExternalId(MeteoAlert meteoAlert);

    List<MeteoAlert> fetchAll();

    void deleteAll();

}
