package com.thedariusz.warnme.twitter.repository;

import com.thedariusz.warnme.MeteoAlertCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeteoAlertCategorySpringDao extends JpaRepository<MeteoAlertCategoryEntity, Long> {



}
