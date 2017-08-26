package com.github.paniclab.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordUtilTest {
    @Test
    public void test() throws Exception {
        PasswordUtil util = new PasswordUtil();
        final String SALT = util.salt();
        final String PASS = "Огромный секрет";

        String expected = util.getHashed(SALT + PASS);


        String anotherPass = "Огромный секрет";
        assertEquals(expected, util.getHashed(SALT + anotherPass));
    }
}