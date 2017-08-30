package com.github.paniclab.models;

import java.util.Objects;

class StageImpl implements Stage {
    private int attemptCount;
    private String attempt;
    private String legend;
    private boolean isWon;

    StageImpl() {
        this.isWon = false;
    }

    @Override
    public void setAttemptCount(int attemptNumber) {
        this.attemptCount = attemptNumber;
    }

    @Override
    public int getAttemptCount() {
        return attemptCount;
    }

    @Override
    public String getAttempt() {
        return attempt;
    }

    @Override
    public void setAttempt(String attempt) {
        this.attempt = attempt;
    }

    @Override
    public void setLegend(String legend) {
        this.legend = legend;
    }

    @Override
    public String getLegend() {
        return this.legend;
    }

    @Override
    public void setWon(boolean isWon) {
        this.isWon = isWon;
    }

    @Override
    public boolean isWon() {
        return isWon;
    }

    @Override
    public int hashCode() {
        return Objects.hash(attemptCount, attempt, legend, isWon);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(!(this.hashCode() == obj.hashCode())) return false;
        if(!(obj instanceof Stage)) return false;
        Stage other = Stage.class.cast(obj);
        return this.getAttemptCount() == other.getAttemptCount() &&
                this.getAttempt().equals(other.getAttempt()) &&
                this.getLegend().equals(other.getLegend()) &&
                this.isWon() == other.isWon();
    }
}
