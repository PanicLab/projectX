package com.github.paniclab.models;


import com.github.paniclab.utils.PasswordUtil;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

class ProfileImpl implements Profile {
    private final String name;
    private final String password;


    ProfileImpl() {
        this.name = "";
        this.password = "";
    }

    ProfileImpl(String name) {
        this.name = name;
        this.password = "";
    }

    ProfileImpl(String name, String password) {
/*        String salt;
        try {
            PasswordUtil util = new PasswordUtil();
            salt = util.salt();
            password = util.getHashed(salt + password);
        } catch (NoSuchAlgorithmException e) {
            password = "";
            name = "";
            salt = "";
            e.printStackTrace();
        }*/
        this.name = name;
        this.password = password;
    }

    @Override
    public String userName() {
        return name;
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public boolean isEmpty() {
        return this.equals(Profile.EMPTY);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.hashCode() != obj.hashCode()) return false;
        if (!(obj instanceof Profile)) return false;
        Profile other = Profile.class.cast(obj);
        return this.name.equals(other.userName());
    }

    @Override
    public String toString() {
        String result = "Объект: " + getClass().getCanonicalName() + ". " +
        "@" + hashCode() + ". Имя: " + name + ". ";
        return result;
    }
}
