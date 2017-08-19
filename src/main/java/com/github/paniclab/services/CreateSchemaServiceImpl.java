package com.github.paniclab.services;

import org.h2.tools.RunScript;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class CreateSchemaServiceImpl implements CreateSchemaService {
    private static final Logger LOGGER = Logger.getAnonymousLogger();
    private Connection connection;

    public CreateSchemaServiceImpl(Connection c) {
        connection = c;
    }

    @Override
    public boolean createSchema() {
        Path path = Paths.get("/resources/sql/createSchema.sql");
        path = toAbsolute(path);
        try {
            RunScript.execute(connection, Files.newBufferedReader(path));
            connection.close();
            LOGGER.info("Sql script createSchema executed successfully");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return true;
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
        Path path = Paths.get("/resources/sql/dropSchema.sql").toAbsolutePath();
        path = toAbsolute(path);
        try {
            RunScript.execute(connection, Files.newBufferedReader(path));
            connection.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        LOGGER.info("Sql script dropSchema executed successfully");
        return true;
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
