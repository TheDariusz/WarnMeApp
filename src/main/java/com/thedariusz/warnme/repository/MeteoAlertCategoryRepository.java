package com.thedariusz.warnme.repository;

import com.thedariusz.warnme.repository.entity.MeteoAlertCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface MeteoAlertCategoryRepository extends JpaRepository<MeteoAlertCategoryEntity, Long> {
    List<MeteoAlertCategoryEntity> findMeteoAlertCategoryEntitiesByName(String name);
    Set<MeteoAlertCategoryEntity> findMeteoAlertCategoryEntitiesByNameIn(Set<String> names);
    Set<MeteoAlertCategoryEntity> findByNameIn(Set<String> names);
    Optional<MeteoAlertCategoryEntity> findMeteoAlertCategoryEntityByName(String name);

}
