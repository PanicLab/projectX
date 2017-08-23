package com.github.paniclab;

import com.github.paniclab.utils.PasswordUtil;

import java.io.PrintStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class dqwd {
    public static void main(String[] args) {
        PasswordUtil util = new PasswordUtil();
        List<String> passwords = new ArrayList<>(10);
        passwords.add("1111");
        passwords.add("А роза упала на лапу Азора");
        passwords.add("Top Secret");
        passwords.add("love");
        passwords.add("qwerty");

        final PrintStream OUT = System.out;
        for (String pass : passwords) {
            OUT.print("Пароль: ");
            OUT.println(pass);

            String safePass = "n/a";
            String hashPass = "n/a";
            String salt = "n/a";
            try {
                hashPass = util.getHashed(pass);
                salt = util.salt();
                safePass = util.getSafe(pass);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            OUT.print("Херишрованный: ");
            OUT.println(hashPass);
            OUT.print("Безопасный: ");
            OUT.println(safePass);
            OUT.print("Соль: ");
            OUT.println(salt);

            OUT.println("Длина пароля=" + pass.length());
            OUT.println("Длина хешированного=" + hashPass.length());
            OUT.println("Длина безопасного=" + safePass.length());
            OUT.println("Длина соли=" + salt.length());
        }
    }
}
