package com.thedariusz.warnme.repository;

import com.thedariusz.warnme.MeteoAlertDao;
import com.thedariusz.warnme.repository.entity.MeteoAlertEntity;
import com.thedariusz.warnme.repository.entity.MeteoAlertMapper;
import com.thedariusz.warnme.MeteoAlert;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class MeteoAlertRepository implements MeteoAlertDao {

    private final MeteoAlertJpaRepository meteoAlertJpaRepository;
    private final MeteoAlertMapper meteoAlertMapper = new MeteoAlertMapper();

    public MeteoAlertRepository(MeteoAlertJpaRepository meteoAlertJpaRepository) {
        this.meteoAlertJpaRepository = meteoAlertJpaRepository;
    }

    @Override
    @Transactional
    public void save(MeteoAlert meteoAlert) {
        meteoAlertJpaRepository.save(meteoAlertMapper.toEntity(meteoAlert));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeteoAlert> fetchExisting(List<String> externalIds) {
        return meteoAlertJpaRepository.findByExternalIdIn(externalIds).stream()
                .map(meteoAlertMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeteoAlert> fetchAll() {
        final List<MeteoAlertEntity> entities = meteoAlertJpaRepository.findByOrderByCreationDateDesc();
        return entities.stream()
                .map(meteoAlertMapper::toModel)
                .collect(Collectors.toList());

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OffsetDateTime> getLatestCreatedAt() {
        return meteoAlertJpaRepository.findFirstByOrderByCreatedAtDesc()
                .map(MeteoAlertEntity::getCreatedAt);
    }

    @Override
    public void deleteAll() {
        meteoAlertJpaRepository.deleteAll();
    }
}
