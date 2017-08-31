package com.github.paniclab.services;

import com.github.paniclab.models.Profile;
import com.github.paniclab.utils.PasswordUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

class ProfileServiceImpl implements ProfileService {
    private static final Logger LOGGER = Logger.getAnonymousLogger();
    private final Connection connection;

    ProfileServiceImpl(Connection conn) {
        this.connection = conn;
    }


    @Override
    public boolean isExist(Profile profile) {
        final int count;
        String sql = "SELECT COUNT(*) FROM GAME_USERS WHERE NAME = '" + profile.userName() + "'";

        try (Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.severe("Не удалось проверить существование профиля. Ошибка при обращении к БД.");
            return false;
        }

        switch (count) {
            case 0: {
                LOGGER.info("Профиль " + profile + "отсутствует в БД.");
                return false;
            }
            case 1: {
                LOGGER.info("Профиль " + profile + "уже существует.");
                return true;
            }
            default: {
                throw new InternalError("Обнаружены проблемы с целостностью данных (несколько пользователей " +
                        "с одинаковыми именами). Обратитесь к разработчику.");
            }
        }
    }

/*    @Override
    public boolean isValid(Profile profile) {
        String sql = "SELECT SALT FROM GAME_USERS WHERE NAME = " + profile.userName();

        try (Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            if (rs.wasNull()) return false;
            String password = rs.getString("PASSWORD");
            if(password.equals(profile.password())) return true;
            if (rs.next()) {
                throw new InternalError("Обнаружена проблема с целостностью данных - два одинаковых имени пользователя" +
                        " в базе данных. Обратитесь к разработчику.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }*/

    @Override
    public boolean isPasswordValid(Profile profile) {
        String salt;
        String databasePassword;
        String sql;

        try (Statement statement = connection.createStatement()){
            sql = "SELECT SALT, PASSWORD FROM GAME_USERS WHERE NAME = '" + profile.userName() + "'";
            ResultSet rs = statement.executeQuery(sql);
            if(!(rs.next())) throw new InternalError("Пользователя с именем " + profile.userName() + " не существует!");
            salt = rs.getString("SALT");
            databasePassword = rs.getString("PASSWORD");
            if (rs.next()) {
                throw new InternalError("Обнаружена проблема с целостностью данных - два одинаковых имени пользователя" +
                        " в базе данных. Обратитесь к разработчику.");
            }

            String hashedProfilePassword = getHashedPassword(salt + profile.password());
            LOGGER.info("Введенный пароль после хеширования: " + hashedProfilePassword);
            LOGGER.info("Пароль из базы данных: " + databasePassword);
            return hashedProfilePassword.equals(databasePassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String getHashedPassword(String saltAndPass) {
        String result = null;
        try {
            PasswordUtil util = new PasswordUtil();
            result = util.getHashed(saltAndPass);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public boolean saveNew(Profile profile) {
        String salt;
        String password;
        try {
            PasswordUtil util = new PasswordUtil();
            salt = util.salt();
            password = util.getHashed(salt + profile.password());
            LOGGER.info("Хеширование пароля завершилось успешно.");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.severe("Не удалось сохранить профиль " + profile + ". Операция хеширования пароля не удалась.");
            e.printStackTrace();
            return false;
        }

        String sql = String.format("INSERT INTO GAME_USERS (NAME, SALT, PASSWORD) VALUES ('%s', '%s', '%s')",
                profile.userName(), salt, password);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            LOGGER.info("Сохранение профиля прошло успешно. Профиль: " + profile);
            return true;
        } catch (SQLException e) {
            LOGGER.severe("Не удалось сохранить профиль " + profile + ". Ошибка при обращении к БД.");
            e.printStackTrace();
            return false;
        }
    }
}
