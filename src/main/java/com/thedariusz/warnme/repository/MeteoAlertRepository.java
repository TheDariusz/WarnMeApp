package com.thedariusz.warnme.repository;

import com.thedariusz.warnme.MeteoAlert;
import com.thedariusz.warnme.MeteoAlertDao;
import com.thedariusz.warnme.repository.entity.MeteoAlertCategoryEntity;
import com.thedariusz.warnme.repository.entity.MeteoAlertEntity;
import com.thedariusz.warnme.repository.entity.MeteoAlertMapper;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


public class MeteoAlertRepository implements MeteoAlertDao {

    private final MeteoAlertJpaRepository meteoAlertJpaRepository;
    private final MeteoAlertCategoryJpaRepository categoryJpaRepository;
    private final MeteoAlertMapper meteoAlertMapper = new MeteoAlertMapper();

    public MeteoAlertRepository(MeteoAlertJpaRepository meteoAlertJpaRepository, MeteoAlertCategoryJpaRepository categoryRepository) {
        this.meteoAlertJpaRepository = meteoAlertJpaRepository;
        this.categoryJpaRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void save(MeteoAlert meteoAlert) {
        MeteoAlertEntity meteoAlertEntity = meteoAlertMapper.toEntity(meteoAlert);
        Set<String> categoryNames = meteoAlert.getCategories();
        Set<MeteoAlertCategoryEntity> categoryEntities = categoryJpaRepository.findAllByNameIn(categoryNames);
        
        
        meteoAlertEntity.getCategories();
        
        
        meteoAlertJpaRepository.save(meteoAlertEntity);
    }

    @Override
    @Transactional
    public void saveAllWithCategories(List<MeteoAlert> meteoAlerts) {
        Set<MeteoAlertCategoryEntity> existingCategories = persistCategories(meteoAlerts);

        final List<MeteoAlertEntity> alertsToSave = meteoAlerts.stream()
                .map(meteoAlert -> meteoAlertEntityWithCategories(existingCategories, meteoAlert))
                .collect(Collectors.toList());

        meteoAlertJpaRepository.saveAll(alertsToSave);
    }

    private MeteoAlertEntity meteoAlertEntityWithCategories(Set<MeteoAlertCategoryEntity> existingCategories, MeteoAlert meteoAlert) {
        MeteoAlertEntity meteoAlertEntity = meteoAlertMapper.toEntity(meteoAlert);
        Set<String> categories = meteoAlert.getCategories();
        Set<MeteoAlertCategoryEntity> entities = existingCategories.stream()
                .filter(categoryEntity -> categories.contains(categoryEntity.getName()))
                .collect(Collectors.toSet());
        meteoAlertEntity.setCategories(entities);
        return meteoAlertEntity;
    }

    private Set<MeteoAlertCategoryEntity> persistCategories(List<MeteoAlert> meteoAlerts) {
        Set<String> categories = meteoAlerts.stream()
                .flatMap(meteoAlert -> meteoAlert.getCategories().stream())
                .collect(Collectors.toSet());

        Set<MeteoAlertCategoryEntity> existingEntities = categoryJpaRepository.findAllByNameIn(categories);
        Set<String> existingCategories = existingEntities.stream()
                .map(MeteoAlertCategoryEntity::getName)
                .collect(Collectors.toSet());

        categories.removeAll(existingCategories);

        Set<MeteoAlertCategoryEntity> categoriesToSave = categories.stream()
                .map(MeteoAlertCategoryEntity::new)
                .collect(Collectors.toSet());

        List<MeteoAlertCategoryEntity> savedEntities = categoryJpaRepository.saveAll(categoriesToSave);

        existingEntities.addAll(savedEntities);
        return existingEntities;
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
