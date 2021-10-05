package com.thedariusz.warnme.repository;

import com.thedariusz.warnme.repository.entity.MeteoAlertCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface MeteoAlertCategoryJpaRepository extends JpaRepository<MeteoAlertCategoryEntity, Long> {
    
    Optional<MeteoAlertCategoryEntity> findMeteoAlertCategoryEntityByName(String name);
    
    Set<MeteoAlertCategoryEntity> findAllByNameIn(Set<String> names);
}
