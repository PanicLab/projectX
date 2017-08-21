package com.github.paniclab.services;

import com.github.paniclab.models.Profile;

public interface ProfileService {
    boolean isExist(Profile profile);
    boolean isValid(String password);
    boolean save(Profile profile);
}
