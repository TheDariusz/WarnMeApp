package com.thedariusz.warnme;

import com.thedariusz.warnme.twitter.MeteoAlert;

import java.time.OffsetDateTime;
import java.util.List;

public interface MeteoAlertDao {

    void save(MeteoAlert meteoAlert);

    List<MeteoAlert> fetchExisting(List<String> externalIds);

    List<MeteoAlert> fetchAll();

    OffsetDateTime getLatestRecordDateTime();


    void deleteAll();

}
