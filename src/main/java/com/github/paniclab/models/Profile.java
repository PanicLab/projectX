/*
* Абстракция, представляющая собой профиль игрока. Используется при авторизации.
* Два профиля, чьи методы userName() возвращают одинаковые значения, эквивалентны.
*/
package com.github.paniclab.models;


public interface Profile {
    Profile EMPTY = new ProfileImpl();

    static Profile valueOf(String name) {
        return new ProfileImpl(name);
    }

    static Profile valueOf(String name, String password) {
        return new ProfileImpl(name, password);
    }


    String userName();
    String password();
    boolean isEmpty();
}
