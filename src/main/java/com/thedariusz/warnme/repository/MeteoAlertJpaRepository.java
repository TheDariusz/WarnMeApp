package com.thedariusz.warnme.repository;

import com.thedariusz.warnme.repository.entity.MeteoAlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeteoAlertJpaRepository extends JpaRepository<MeteoAlertEntity, Long> {

    List<MeteoAlertEntity> findByExternalIdIn(List<String> externalIds);
    List<MeteoAlertEntity> findByOrderByCreationDateDesc();
    Optional<MeteoAlertEntity> findFirstByOrderByCreatedAtDesc();

}
