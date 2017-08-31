package com.github.paniclab.services;


import javax.servlet.ServletContext;
import java.nio.file.Path;
import java.sql.Connection;

public interface CreateSchemaService extends AutoCloseable {

    boolean createSchema();
    boolean dropSchema();

    @Override
    void close();

    static CreateSchemaService create(Connection connection) {
        return new CreateSchemaServiceImpl(connection);
    }

    static CreateSchemaService create(ServletContext cxt) {
        return new CreateSchemaServiceImpl(cxt);
    }
}
