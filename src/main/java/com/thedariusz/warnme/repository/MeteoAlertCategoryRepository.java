package com.thedariusz.warnme.repository;

import com.thedariusz.warnme.repository.entity.MeteoAlertCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeteoAlertCategoryRepository extends JpaRepository<MeteoAlertCategoryEntity, Long> {
    MeteoAlertCategoryEntity findMeteoAlertCategoryEntityByName(String name);

}
