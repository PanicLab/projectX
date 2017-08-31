package com.github.paniclab.services;


import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.tools.RunScript;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

class CreateSchemaServiceImpl implements CreateSchemaService {
    private static final Logger LOGGER = Logger.getAnonymousLogger();
    private Connection connection;
    private Path scriptPath;

    CreateSchemaServiceImpl(Connection c) {
        connection = c;
    }

    CreateSchemaServiceImpl(ServletContext cxt) {
        LOGGER.info("Попытка создания службы CreateSchemaService...");
        String relativeSQLScriptPath = cxt.getInitParameter("db.schema_script_path");
        scriptPath = Paths.get(cxt.getRealPath("/"), relativeSQLScriptPath);
        LOGGER.info("Путь к SQL скриптам определен как: " + scriptPath);

        JdbcConnectionPool pool = (JdbcConnectionPool)cxt.getAttribute("connection_pool");
        if(pool == null) throw new IllegalStateException("Не удалось получить объект JdbcConnectionPool из " +
                "контекста приложения. Возможно его сначалу нужно туда поместить.");
        LOGGER.info(pool == null ? "" : "Объект JdbcConnectionPool успешно извлечен из контекста приложения.");

        try {
            connection = pool.getConnection();
            LOGGER.info("Объект Connection получен успешно.");
            LOGGER.info("СОздание службы CreateSchemaService завершилось успешно.");
        } catch (SQLException e) {
            LOGGER.severe("Не удалось получить объект Connection. Ошибка при обращении к БД");
            LOGGER.severe("Не удалось создать службу CreateSchemaService.");
            e.printStackTrace();
        }
    }

    @Override
    public boolean createSchema() {
        LOGGER.info("Объект CreateSchemaService пытается создать схему БД при помощи скриптового файла...");
        Path path = Paths.get(scriptPath.toString(), "createSchema.sql");
        LOGGER.info("Путь к скриптовому файлу определен как " + path.toString());
        try {
            RunScript.execute(connection, Files.newBufferedReader(path));
            connection.close();
            LOGGER.info("Схема БД успешно создана при помощи скриптового файла.");
            return true;
        } catch (SQLException | IOException e) {
            LOGGER.severe("Не удалось создать схему, ошибка при обращении к БД.");
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public boolean dropSchema() {
        LOGGER.info("Объект CreateSchemaService пытается удалить схему БД при помощи скриптового файла...");
        Path path = Paths.get(scriptPath.toString(), "dropSchema.sql");
        LOGGER.info("Путь к скриптовому файлу определен как " + path.toString());
        try {
            RunScript.execute(connection, Files.newBufferedReader(scriptPath));
            connection.close();
            LOGGER.info("Sql script dropSchema executed successfully");
            return true;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void close() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
