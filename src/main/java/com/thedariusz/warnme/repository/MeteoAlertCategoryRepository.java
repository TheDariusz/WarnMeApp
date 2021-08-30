package com.thedariusz.warnme.repository;

import com.thedariusz.warnme.repository.entity.MeteoAlertCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeteoAlertCategoryRepository extends JpaRepository<MeteoAlertCategoryEntity, Long> {
    Optional<MeteoAlertCategoryEntity> findMeteoAlertCategoryEntityByName(String name);

}
