package com.github.paniclab.models;

import java.util.List;

public interface GameSession {

    static GameSession newInstance() {
        return new GameSessionImpl();
    }

    String getNumber();
    void makeNewAttempt(int attempt);
    List<Stage> getStageList();
    boolean isOver();
}
