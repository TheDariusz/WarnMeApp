package com.thedariusz.warnme.twitter.repository;

import com.thedariusz.warnme.MeteoAlertDao;
import com.thedariusz.warnme.twitter.MeteoAlert;
import com.thedariusz.warnme.twitter.MeteoAlertEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class PostgresMeteoAlertDao implements MeteoAlertDao {

    private final MeteoAlertSpringDao repository;
    private final MeteoAlertMapper meteoAlertMapper = new MeteoAlertMapper();

    public PostgresMeteoAlertDao(MeteoAlertSpringDao repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public void save(MeteoAlert meteoAlert) {
        repository.save(meteoAlertMapper.toEntity(meteoAlert));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeteoAlert> fetchExisting(List<String> externalIds) {
        return repository.findByExternalIdIn(externalIds).stream()
                .map(meteoAlertMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeteoAlert> fetchAll() {
        final List<MeteoAlertEntity> entities = repository.findByOrderByCreationDateDesc();
        return entities.stream()
                .map(meteoAlertMapper::toModel)
                .collect(Collectors.toList());

    }

    @Override
    public OffsetDateTime getLatestRecordDateTime() {
        return repository.findFirstByOrderByRecordCreatedDateDesc().getRecordCreatedDate();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }



}
