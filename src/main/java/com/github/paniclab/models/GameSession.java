package com.github.paniclab.models;

import java.util.List;

public interface GameSession {

    static GameSession newInstance() {
        return new GameSessionImpl();
    }

    String getNumber();
    int getStageCount();
    void makeNewAttempt(String attempt);
    List<Stage> getStageList();
    boolean isOver();
}
