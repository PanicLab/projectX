package com.github.paniclab.services;

import com.github.paniclab.models.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.util.List;

public interface UserService {
    static UserService newInstance(Connection conn) {
        return new UserServiceImpl(conn);
    }

    static UserService newInstance(HttpServletRequest request) {
        return new UserServiceImpl(request);
    }

    List<User> getUsers();
    User getUserByName(String userName);
    void persist(User user);
}
