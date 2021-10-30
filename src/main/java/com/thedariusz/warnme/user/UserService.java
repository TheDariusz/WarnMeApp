package com.thedariusz.warnme.user;


import com.thedariusz.warnme.user.repository.UserParametersRepository;
import com.thedariusz.warnme.user.repository.entity.UserEntity;
import com.thedariusz.warnme.user.repository.entity.UserParametersEntity;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

public class UserService {

    private static final OffsetDateTime DEFAULT_REFRESH_DATE = OffsetDateTime.of(2021, 10, 1, 0, 0, 1, 0, ZoneOffset.UTC);
    private final UserDao dao;
    private final UserParametersRepository parametersRepository;

    public UserService(UserDao dao, UserParametersRepository parametersRepository) {
        this.dao = dao;
        this.parametersRepository = parametersRepository;
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

    public OffsetDateTime lastDateTwitterRefreshedForUser(Long userId) {
        UserParametersEntity userParametersEntity = parametersRepository.findFirstByUserIdOrderBySourceDateRefreshDesc(userId);
        if (userParametersEntity==null) {
            return DEFAULT_REFRESH_DATE;
        } else {
            return userParametersEntity.getSourceDateRefresh();
        }
    }

    public OffsetDateTime lastDateTwitterRefreshedForApplication() {
        UserParametersEntity userParametersEntity = parametersRepository.findFirstByOrderBySourceDateRefreshDesc();
        if (userParametersEntity==null) {
            return DEFAULT_REFRESH_DATE;
        } else {
            return userParametersEntity.getSourceDateRefresh();
        }
    }

    public void saveRefreshDateForUser(Long loggedUserId, String endTime) {
        UserParametersEntity userParametersEntity = new UserParametersEntity();
        Optional<UserEntity> user = dao.findByUserId(loggedUserId);
        if (user.isPresent()) {
            userParametersEntity.setUser(user.get());
            userParametersEntity.setSourceDateRefresh(OffsetDateTime.parse(endTime));
            parametersRepository.save(userParametersEntity);
        }
    }
}
