package com.github.paniclab.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

public class PasswordUtil {

    public String getHashed(String password) throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.reset();
        messageDigest.update(password.getBytes());

        byte[] mdArray = messageDigest.digest();
        StringBuilder sb = new StringBuilder(mdArray.length * 2);
        for (byte b : mdArray) {
            int v = b & 0xff;
            if (v < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }

    public String salt() {
        Random gen = new SecureRandom();
        byte[] salt = new byte[32];
        gen.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public String getSafe(String password) throws NoSuchAlgorithmException {
        return getHashed(password + salt());
    }
}
