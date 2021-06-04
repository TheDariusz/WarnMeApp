package com.thedariusz.warnme.user.repository;

import com.thedariusz.warnme.user.User;

public interface UserDao {
    User findByUserName(String name);
    void saveUser(User user);
}
