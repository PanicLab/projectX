/*
* Абстракция, представляющая собой профиль игрока. Используется при авторизации.
* Два профиля, чьи методы userName() возвращают одинаковые значения, эквивалентны.
*/
package com.github.paniclab.models;

public interface Profile {
    Profile EMPTY = new ProfileImpl("", "");

    static Profile of(String name) {
        return new ProfileImpl(name);
    }


    String userName();
    String password();
    String getSafePassword();
    boolean isEmpty();
    boolean isExist();
    default boolean isNotExist() {
        return (!isExist());
    }
}
