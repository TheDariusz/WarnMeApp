package com.thedariusz.warnme;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface MeteoAlertDao {

    void save(MeteoAlert meteoAlert);

    List<MeteoAlert> fetchExisting(List<String> externalIds);

    List<MeteoAlert> fetchAll();

    Optional<OffsetDateTime> getLatestCreatedAt();

    void deleteAll();

}
