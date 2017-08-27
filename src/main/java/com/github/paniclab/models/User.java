package com.github.paniclab.models;

public interface User {
    static User newInstance() {
        return new UserImpl();
    }

    long getId();
    void setId(long id);

    String getName();
    void setName(String name);

    int getBestResult();
    void setBestResult(int bestResult);

    int getLastResult();
    void setLastResult(int lastResult);

    float getAverageResult();
    void setAverageResult(float averageResult);

    int getAuthority();
    void setAuthority(int authority);
}
