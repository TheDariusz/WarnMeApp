package com.thedariusz.warnme.user;


import com.thedariusz.warnme.user.repository.entity.UserEntity;

import java.util.Optional;

public class UserService {

    private final UserDao dao;

    public UserService(UserDao dao) {
        this.dao = dao;
    }

    public boolean existUser(UserDto userDto) {
        return dao.findByUserName(userDto.getUsername()).isPresent();
    }

    public void saveUser(UserDto userDto) {
        dao.saveUser(UserEntity.toEntity(userDto));
    }

    public Long getUserId(String userName) {
        return dao.getUserId(userName);
    }
}
