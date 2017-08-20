package com.github.paniclab.services;


import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

class CreateSchemaServiceImpl implements CreateSchemaService {
    private static final Logger LOGGER = Logger.getAnonymousLogger();
    private Connection connection;

    CreateSchemaServiceImpl(Connection c) {
        connection = c;
    }

    @Override
    public boolean createSchema() {
/*        Path path = Paths.get("/resources/sql/createSchema.sql");
        path = toAbsolute(path);
        try {
            RunScript.execute(connection, Files.newBufferedReader(path));
            connection.close();
            LOGGER.info("Sql script createSchema executed successfully");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }*/

        String sql;
        try (Statement statement = connection.createStatement()) {
            sql = "CREATE TABLE IF NOT EXISTS USERS (ID BIGINT AUTO_INCREMENT, NAME VARCHAR(255) NOT NULL)";
            statement.executeUpdate(sql);

            sql = "ALTER TABLE USERS ADD CONSTRAINT IF NOT EXISTS users_pk PRIMARY KEY(ID)";
            statement.executeUpdate(sql);
            LOGGER.info("Sql script createSchema executed successfully");
            return true;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Path toAbsolute(Path path) {
        URI uri = null;
        try {
            uri = getClass().getProtectionDomain().getCodeSource().getLocation()
                    .toURI();
        } catch (URISyntaxException e) {
            LOGGER.severe("Не удалось определить путь к ресурсу.");
            throw new InternalError(e);
        }

        return Paths.get(uri);
    }


    @Override
    public boolean dropSchema() {
/*        Path path = Paths.get("/resources/sql/dropSchema.sql").toAbsolutePath();
        path = toAbsolute(path);
        try {
            RunScript.execute(connection, Files.newBufferedReader(path));
            connection.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        LOGGER.info("Sql script dropSchema executed successfully");
        return true;*/

        String sql;
        try (Statement statement = connection.createStatement()) {
            sql = "DROP TABLE IF EXISTS USERS";
            statement.executeUpdate(sql);
            LOGGER.info("Sql script dropSchema executed successfully");
            return true;
        }catch (SQLException e) {
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
