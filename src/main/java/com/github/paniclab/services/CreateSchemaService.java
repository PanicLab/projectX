package com.github.paniclab.services;

import java.sql.Connection;

public interface CreateSchemaService extends AutoCloseable {

    boolean createSchema();
    boolean dropSchema();

    @Override
    void close();

    static CreateSchemaService get(Connection connection) {
        return new CreateSchemaServiceImpl(connection);
    }
}
