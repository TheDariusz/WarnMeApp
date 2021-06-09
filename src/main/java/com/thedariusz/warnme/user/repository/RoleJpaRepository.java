package com.thedariusz.warnme.user.repository;

import com.thedariusz.warnme.user.repository.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleJpaRepository extends JpaRepository<RoleEntity, Integer> {
    RoleEntity findByName(String name);
}
