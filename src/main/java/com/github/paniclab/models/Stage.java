package com.github.paniclab.models;

public interface Stage {
    static Stage newInstance() {
        return new StageImpl();
    }

    int getAttemptNumber();
    void setAttemptNumber(int attemptNumber);
    String getLegend();
    void setLegend(String legend);
    void setWonStage(boolean isWon);
    boolean isWonStage();
}
