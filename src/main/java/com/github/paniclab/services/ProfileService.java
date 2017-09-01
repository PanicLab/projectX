package com.github.paniclab.services;

import com.github.paniclab.models.Profile;

import java.sql.Connection;

public interface ProfileService {
    static ProfileService newInstance(Connection connection) {
        return new ProfileServiceImpl(connection);
    }

    boolean isExist(Profile profile);
    boolean isPasswordValid(Profile profile);
    boolean persist(Profile profile);
}
