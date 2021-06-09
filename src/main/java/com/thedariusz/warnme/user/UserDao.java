package com.thedariusz.warnme.user;

import com.thedariusz.warnme.user.repository.entity.UserEntity;

import java.util.Optional;

public interface UserDao {

    Optional<UserDto> findByUserName(String name);

    void saveUser(UserEntity userEntity);
}
