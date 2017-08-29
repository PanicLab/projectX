package com.github.paniclab.models;

import java.util.Objects;

class StageImpl implements Stage {
    private int attemptNumber;
    private String legend;
    private boolean isWonStage;

    StageImpl() {
        this.isWonStage = false;
    }

    @Override
    public void setAttemptNumber(int attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    @Override
    public int getAttemptNumber() {
        return attemptNumber;
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
    public void setWonStage(boolean isWon) {
        isWonStage = isWon;
    }

    @Override
    public boolean isWonStage() {
        return isWonStage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(attemptNumber, legend, isWonStage);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(!(this.hashCode() == obj.hashCode())) return false;
        if(!(obj instanceof Stage)) return false;
        Stage other = Stage.class.cast(obj);
        return this.getAttemptNumber() == other.getAttemptNumber() &&
                this.getLegend().equals(other.getLegend()) && this.isWonStage() == other.isWonStage();
    }
}
