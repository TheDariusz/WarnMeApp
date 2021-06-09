package com.thedariusz.warnme.user.repository;

import com.thedariusz.warnme.user.UserDao;
import com.thedariusz.warnme.user.UserDto;
import com.thedariusz.warnme.user.repository.entity.RoleEntity;
import com.thedariusz.warnme.user.repository.entity.UserEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

public class UserRepository implements UserDao {

    private final UserJpaRepository userDao;
    private final RoleJpaRepository roleDao;
    private final BCryptPasswordEncoder encoder;

    public UserRepository(UserJpaRepository userDao, RoleJpaRepository roleDao, BCryptPasswordEncoder encoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.encoder = encoder;
    }

    @Override
    public Optional<UserDto> findByUserName(String username) {
        return userDao.findByUsername(username)
                .map(UserEntity::toUserDto);
    }

    @Override
    public void saveUser(UserEntity userEntity) {
        userEntity.setPassword(encoder.encode(userEntity.getPassword()));
        userEntity.setEnabled(1);
        RoleEntity userRoleEntity = roleDao.findByName("ROLE_USER");
        userEntity.setRoles(new HashSet<>(Arrays.asList(userRoleEntity)));
        userDao.save(userEntity);
    }
}
