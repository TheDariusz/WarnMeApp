package com.thedariusz.warnme;

import com.thedariusz.warnme.twitter.MeteoAlert;

import java.util.List;

public interface MeteoAlertDao {

    void save(MeteoAlert meteoAlert);

    List<MeteoAlert> fetchExisting(List<String> externalIds);

    List<MeteoAlert> fetchAll();

    void deleteAll();

}
