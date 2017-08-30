package com.github.paniclab.models;

public interface Stage {
    static Stage newInstance() {
        return new StageImpl();
    }

    int getAttemptCount();
    void setAttemptCount(int attemptCount);
    String getAttempt();
    void setAttempt(String attempt);
    String getLegend();
    void setLegend(String legend);
    void setWon(boolean isWon);
    boolean isWon();
}
