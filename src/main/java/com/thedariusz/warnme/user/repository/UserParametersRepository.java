package com.thedariusz.warnme.user.repository;

import com.thedariusz.warnme.user.repository.entity.UserParametersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserParametersRepository extends JpaRepository<UserParametersEntity, Long> {
    UserParametersEntity findFirstByUserIdOrderBySourceDateRefreshDesc(Long userId);
    UserParametersEntity findFirstByOrderBySourceDateRefreshDesc();
    UserParametersEntity findFirstBySourceIdOrderBySourceDateRefreshDesc(String sourceId);
}
