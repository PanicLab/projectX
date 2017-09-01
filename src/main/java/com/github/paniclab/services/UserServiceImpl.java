package com.github.paniclab.services;

import com.github.paniclab.models.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getAnonymousLogger();
    private final Connection connection;

    UserServiceImpl(Connection conn) {
        connection = conn;
    }

    UserServiceImpl(HttpServletRequest request) {
        Connection connection = (Connection)request.getSession().getAttribute("connection");
        LOGGER.info(connection == null ? "Объект Connection равен null!" : "Объект Connection получен успешно.");
        this.connection = connection;
    }

    @Override
    public List<User> getUsers() {
        LOGGER.info("Объект UserService пытается возвратить список игроков...");
        List<User> result = new ArrayList<>();
        String sql;

        try (Statement statement = connection.createStatement()){
            sql = "SELECT ID, NAME, BEST_RESULT, LAST_RESULT, AVERAGE_RESULT, ATTEMPTS_COUNT, AUTHORITY FROM GAME_USERS " +
                    "ORDER BY AVERAGE_RESULT NULLS LAST, BEST_RESULT NULLS LAST, LAST_RESULT NULLS LAST, " +
                    "AUTHORITY DESC";
            ResultSet rs = statement.executeQuery(sql);

            User user = extractUserFrom(rs);
            while (user.isNotEmpty()) {
                result.add(user);
                user = extractUserFrom(rs);
            }
            LOGGER.info("Список игроков успешно извлечен из БД." + (result.size() == 0 ? " Однако он пуст." : ""));
        } catch (SQLException e) {
            e.printStackTrace();
            result = Collections.emptyList();
            LOGGER.severe("Ошибка при обращении к БД, объект UserService возвращает пустой список.");
        }

        return result;
    }

    private User extractUserFrom(ResultSet rs) throws SQLException {
        User user = User.newInstance();
        if (rs.next()) {
            user.setId(rs.getLong("ID"));
            user.setName(rs.getString("NAME"));
            user.setBestResult(rs.getInt("BEST_RESULT"));
            user.setLastResult(rs.getInt("LAST_RESULT"));
            user.setAttemptsCount(rs.getInt("ATTEMPTS_COUNT"));
            user.setAverageResult(rs.getFloat("AVERAGE_RESULT"));
            user.setAuthority(rs.getInt("AUTHORITY"));
        }

        return user;
    }


    @Override
    public User getUserByName(String userName) {
        LOGGER.info("Объект UserService пытается возвратить объект USER по имени " + userName + "...");
        String sql;

        try (Statement statement = connection.createStatement()) {
            sql = String.format("SELECT ID, NAME, BEST_RESULT, LAST_RESULT, AVERAGE_RESULT, ATTEMPTS_COUNT, AUTHORITY " +
                    "FROM GAME_USERS WHERE NAME = '%s'", userName);
            ResultSet resultSet = statement.executeQuery(sql);
            User user = extractUserFrom(resultSet);
            if(resultSet.next()) throw new InternalError("Обнаружены проблемы с целостностью данных (несколько " +
                    "пользователей с одинаковыми именами). Обратитесь к разработчику.");
            LOGGER.info("Объект User с именем " + userName + " найден и успешно извлечен из БД.");
            return user;
        } catch (SQLException e) {
            LOGGER.severe("Не удалось извлечь объект User. Ошибка при обращении к БД.");
            e.printStackTrace();
        }

        LOGGER.info("Объект UserService возвращает пустой объект USER.");
        return User.newInstance();
    }

    @Override
    public void persist(User user) {
        String sql;

        LOGGER.info("UserService пытается обновить данные пользователя...");
        try (Statement statement = connection.createStatement()){
            sql = String.format(Locale.US,
                    "UPDATE GAME_USERS SET BEST_RESULT = %d, LAST_RESULT = %d, AVERAGE_RESULT = %.2f, ATTEMPTS_COUNT = %d WHERE ID = %d",
                    user.getBestResult(),
                    user.getLastResult(),
                    user.getAverageResult(),
                    user.getAttemptsCount(),
                    user.getId()
            );
            int rowsAffected = statement.executeUpdate(sql);
            if (rowsAffected == 1) LOGGER.info("Данные пользователя успешно обнавлены.");

        } catch (SQLException e) {
            LOGGER.info("Не удалось обновить данные пользователя. Ошибка при обращении к БД.");
            e.printStackTrace();
        }
    }
}
