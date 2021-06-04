package com.thedariusz.warnme.user.repository;

import com.thedariusz.warnme.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepositoryDao extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
