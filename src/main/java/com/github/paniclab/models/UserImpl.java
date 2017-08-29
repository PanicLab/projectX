package com.github.paniclab.models;


import java.util.Objects;

class UserImpl implements User {
    private long id;
    private String name;
    private int bestResult;
    private int lastResult;
    private float averageResult;
    private int attemptsCount;
    private int authority;

    UserImpl() {
        this.id = -1;
        this.name = "UNKNOWN";
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getBestResult() {
        return bestResult;
    }

    @Override
    public void setBestResult(int bestResult) {
        this.bestResult = bestResult;
    }

    @Override
    public int getLastResult() {
        return lastResult;
    }

    @Override
    public void setLastResult(int lastResult) {
        this.lastResult = lastResult;
    }

    @Override
    public int getAttemptsCount() {
        return attemptsCount;
    }

    @Override
    public void setAttemptsCount(int attemptsCount) {
        this.attemptsCount = attemptsCount;
    }

    @Override
    public float getAverageResult() {
        return averageResult;
    }

    @Override
    public void setAverageResult(float averageResult) {
        this.averageResult = averageResult;
    }

    @Override
    public int getAuthority() {
        return authority;
    }

    @Override
    public void setAuthority(int authority) {
        this.authority = authority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return  false;
        if (this == obj) return true;
        if(!(this.hashCode() == obj.hashCode())) return false;
        if(!(obj instanceof User)) return false;
        User other = User.class.cast(obj);
        return this.getId() == other.getId();
    }

    @Override
    public String toString() {
        final String LS = System.lineSeparator();
        return "Объект User" + LS + "ID: " + getId() + LS + "Имя: " + getName() + LS + "Средний результат: " +
                getAverageResult() + LS + "Лучший результат: " + getBestResult() + LS + "Последний результат: " +
                getLastResult() + LS + "Доп. критерий сортировки: " + getAuthority() + LS;
    }
}
