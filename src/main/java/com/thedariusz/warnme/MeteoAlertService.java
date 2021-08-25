package com.thedariusz.warnme;

import com.thedariusz.warnme.repository.MeteoAlertJpaRepository;
import com.thedariusz.warnme.repository.entity.MeteoAlertCategoryEntity;
import com.thedariusz.warnme.repository.entity.MeteoAlertCategoryMapper;
import com.thedariusz.warnme.repository.entity.MeteoAlertEntity;
import com.thedariusz.warnme.repository.entity.MeteoAlertMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MeteoAlertService {

    private static final Logger logger = LoggerFactory.getLogger(MeteoAlertService.class);

    private static final String EMPTY_STRING = "";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final MeteoAlertDao meteoAlertDao;
    private final MeteoAlertCategoryService twitterCategoryService;
    private final MeteoAlertJpaRepository meteoAlertJpaRepository;

    public MeteoAlertService(MeteoAlertDao meteoAlertDao, MeteoAlertCategoryService twitterCategoryService, MeteoAlertJpaRepository meteoAlertJpaRepository) {
        this.meteoAlertDao = meteoAlertDao;
        this.twitterCategoryService = twitterCategoryService;
        this.meteoAlertJpaRepository = meteoAlertJpaRepository;
    }

    public void save(List<MeteoAlert> meteoAlerts) {
        final MeteoAlertCategoryMapper categoryMapper = new MeteoAlertCategoryMapper();
        final MeteoAlertMapper meteoAlertMapper = new MeteoAlertMapper();

        final List<String> newIds = meteoAlerts.stream().
                map(MeteoAlert::getExternalId)
                .collect(Collectors.toList());

        List<MeteoAlert> existingMeteoAlerts = meteoAlertDao.fetchExisting(newIds);

        final List<MeteoAlert> alertsToSave = meteoAlerts.stream()
                .filter(meteoAlert -> externalIdEquals(existingMeteoAlerts, meteoAlert))
                .collect(Collectors.toList());

        logger.info("Saving {} new alerts", alertsToSave.size());

        for (MeteoAlert meteoAlert : meteoAlerts) {
            final Set<String> categories = meteoAlert.getCategories();
            Set<MeteoAlertCategoryEntity> categoriesToAlert = new HashSet<>();
            final Set<MeteoAlertCategoryEntity> meteoAlertCategoryEntities = twitterCategoryService.mapToEntities(categories);

            meteoAlertCategoryEntities.forEach(categoryEntity -> categoriesToAlert.add(twitterCategoryService.saveCategory(categoryEntity)));

            final MeteoAlertEntity meteoAlertEntity = meteoAlertMapper.toEntity(meteoAlert);
            meteoAlertEntity.setCategories(categoriesToAlert);
            meteoAlertJpaRepository.save(meteoAlertEntity);

        }
    }

    public List<MeteoAlert> getMeteoAlertsFromDb() {
        return meteoAlertDao.fetchAll();
    }

    private boolean externalIdEquals(List<MeteoAlert> existingAlerts, MeteoAlert meteoAlert) {
        for (MeteoAlert alert : existingAlerts) {
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
