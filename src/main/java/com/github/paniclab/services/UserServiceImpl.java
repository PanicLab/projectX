package com.github.paniclab.services;

import com.github.paniclab.models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getAnonymousLogger();
    private final Connection connection;

    UserServiceImpl(Connection conn) {
        connection = conn;
    }

    @Override
    public List<User> getUsers() {
        LOGGER.info("Объект UserService пытается возвратить список игроков...");
        List<User> result = new ArrayList<>();
        String sql;

        try (Statement statement = connection.createStatement()){
            sql = "SELECT ID, NAME, BEST_RESULT, LAST_RESULT, AVERAGE_RESULT, AUTHORITY FROM GAME_USERS ORDER BY " +
                    "AVERAGE_RESULT NULLS LAST, BEST_RESULT NULLS LAST, LAST_RESULT NULLS LAST, AUTHORITY DESC";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                User user = User.newInstance();
                user.setId(rs.getLong("ID"));
                user.setName(rs.getString("NAME"));
                user.setBestResult(rs.getInt("BEST_RESULT"));
                user.setLastResult(rs.getInt("LAST_RESULT"));
                user.setAverageResult(rs.getInt("AVERAGE_RESULT"));
                user.setAuthority(rs.getInt("AUTHORITY"));

                result.add(user);
            }
            LOGGER.info("Список игроков успешно извлечен из БД." + (result.size() == 0 ? " Однако он пуст." : ""));
        } catch (SQLException e) {
            e.printStackTrace();
            result = Collections.emptyList();
            LOGGER.severe("Ошибка при обращении к БД, объект UserService возвращает пустой список.");
        }

        return result;
    }
}