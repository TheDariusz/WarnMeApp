package com.thedariusz.warnme.user;

import com.thedariusz.warnme.user.repository.UserDao;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao dao;

    public UserService(UserDao dao) {
        this.dao = dao;
    }

    public String validateUsername(UserDto userDto) {
        String message = "";

        if (existUser(userDto)) {
            message = "There is already a user registered with the username provided";
        }

        if (emptyUsernameOrPassword(userDto)) {
            message = "Username and password should be not empty!";
        }

        return message;
    }

    private boolean emptyUsernameOrPassword(UserDto userDto) {
        if (userDto.getUsername()==null || userDto.getUsername().isBlank()) {
            return true;
        }

        if (userDto.getPassword()==null || userDto.getPassword().isBlank()) {
            return true;
        }
        return false;
    }

    private boolean existUser(UserDto userDto) {
        User user = dao.findByUserName(
                User.toUser(userDto)
                        .getUsername()
        );
        return user!=null;
    }

    public String validatePassword(UserDto userDto) {
        String message = "";
        if (emptyUsernameOrPassword(userDto)) {
            message = "Username and password should be not empty!";
        }
        return message;
    }

    public void saveUser(UserDto userDto) {
        dao.saveUser(User.toUser(userDto));
    }
}
