package com.github.paniclab.services;

import com.github.paniclab.models.User;

import java.sql.Connection;
import java.util.List;

public interface UserService {
    static UserService newInstance(Connection conn) {
        return new UserServiceImpl(conn);
    }

    List<User> getUsers();
    User getUserByName(String userName);
}
