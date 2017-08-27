package com.github.paniclab.services;

import com.github.paniclab.models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

class UserServiceImpl implements UserService {
    private final Connection connection;

    UserServiceImpl(Connection conn) {
        connection = conn;
    }

    @Override
    public List<User> getUsers() {
        String sql;
        try (Statement statement = connection.createStatement()){
            sql = "SELECT ID, NAME, BEST_RESULT, LAST_RESULT, AUTHORITY FROM GAME_USERS ORDER BY BEST_RESULT DESC" +
                    " NULLS LAST, LAST_RESULT DESC NULLS LAST, AUTHORITY DESC";
            ResultSet rs = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }
}
