package com.thedariusz.warnme.repository;

import com.thedariusz.warnme.repository.entity.MeteoAlertCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface MeteoAlertCategoryRepository extends JpaRepository<MeteoAlertCategoryEntity, Long> {
    List<MeteoAlertCategoryEntity> findMeteoAlertCategoryEntitiesByName(String name);
    Set<MeteoAlertCategoryEntity> findMeteoAlertCategoryEntitiesByNameIn(Set<String> names);
}
