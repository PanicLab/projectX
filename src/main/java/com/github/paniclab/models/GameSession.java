package com.github.paniclab.models;

import java.util.List;

public interface GameSession {

    String getNumber();
    void makeNewAttempt(int attempt);
    List<Stage> getStageList();
    boolean isOver();
}
